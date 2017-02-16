package com.sertecimedco.ruido;

import java.io.File;
import java.nio.file.Files;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megasoftworks.gl.manage.Archivos;
import com.megasoftworks.gl.util.MD5;
import com.megasoftworks.gl.util.NetUtil;
import com.sertecimedco.ruido.clasesComunes.HayArchivoResponse;
import com.sertecimedco.ruido.clasesComunes.SubeArchivoRequest;
import com.sertecimedco.ruido.clasesComunes.SubeArchivoResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class UploadClient {
	static Logger logger = Logger.getLogger(UploadClient.class);
	
	
	public static void main(String[] args) {
		logger.info("Iniciando el programa...");
		Run run = new Run();
		logger.debug("Probando ping al servidor  " + Run.SERVER_IP);
		if (NetUtil.thereIsPing(Run.SERVER_IP) && run.obtieneListadoArchivos()) {
			logger.info("Archivos pendientes por subir: " + run.listadoArchivos.length);
			for (String nombreArchivo : run.listadoArchivos) {
				logger.info("Procesando archivo: " + nombreArchivo);
				hayArchivoLLamada(nombreArchivo);
			}
		} else {
			logger.fatal("No hay conexión al servidor...");
		}
	}
	
	private static void hayArchivoLLamada(String nombreArchivo) {

		String url = "http://" + Run.SERVER_IP + ":"
				+ Run.SERVER_PORT + Run.SERVER_SERVICIO
				+ Run.SERVICIO_HAY_ARCHIVO
				+ "/" + nombreArchivo
				+ "/" + Run.ESTACION;
		logger.debug("Url del web service: " + url);
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(url);
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class);

			if (response.getStatus() != 200) {
				logger.fatal("Failed : HTTP error code : "
					     + response.getStatus());
			} else {
				logger.info("Output from Server .... \n");
				String responseJson = response.getEntity(String.class); 
				logger.info(responseJson);
				ObjectMapper mapper = new ObjectMapper();
				HayArchivoResponse har = mapper.readValue(responseJson, HayArchivoResponse.class);
				if(!har.isHayArchivo()) {
					subeArchivoLLamada(nombreArchivo);
				} else {
					moverArchivo(nombreArchivo);
				}
			}
			
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			logger.fatal(e.toString());
		}
	}
	
	private static void subeArchivoLLamada(String nombreArchivo) {
		RestTemplate restTemplate = new RestTemplate() ;
		String url = "http://" + Run.SERVER_IP + ":"
				+ Run.SERVER_PORT + Run.SERVER_SERVICIO
				+ Run.SERVICIO_SUBE_ARCHIVO;
		logger.debug("Url del web service: " + url);
		try {
			SubeArchivoRequest sar = new SubeArchivoRequest();
			String pathArchivo = Run.pathPorProcesar + File.separator + nombreArchivo;
			
			sar.setEstacion(Run.ESTACION);
			sar.setMd5(MD5.fileMd5Sum(new File(pathArchivo)));
			sar.setNombreArchivo(nombreArchivo);
			sar.setContenido(Files.readAllBytes(new File(pathArchivo).toPath()));
			HttpEntity<Object> entity = new HttpEntity<Object>(sar); 
			ResponseEntity<SubeArchivoResponse> response = restTemplate.exchange(
					url, HttpMethod.POST, entity, SubeArchivoResponse.class);
			if (response.getStatusCode() != HttpStatus.OK) {
				logger.error(response.getStatusCode());
			} else {
				logger.info(response.getBody());
				moverArchivo(nombreArchivo);
			}
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			logger.fatal(e.toString());
		}
	}
	
	private static void moverArchivo(String nombreArchivo) {
		logger.info("Moviendo archivo: " + nombreArchivo);
		Archivos.fileMove(Run.pathPorProcesar + File.separator + nombreArchivo,
				Run.pathProcesados + File.separator +  nombreArchivo);
	}
}
