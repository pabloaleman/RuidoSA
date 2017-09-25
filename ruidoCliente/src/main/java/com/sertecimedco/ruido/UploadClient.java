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
import com.megasoftworks.gl.exceptions.MoverArchivosException;
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
		ComunArchivos comunArchivos = new ComunArchivos();
		logger.debug("Probando ping al servidor  " + Run.SERVER_IP);
		if (NetUtil.thereIsPing(Run.SERVER_IP) && comunArchivos.obtieneListadoArchivos()) {
			logger.info("Archivos pendientes por subir: " + comunArchivos.listadoFicherosDate.length);
			for (String nombreFicheroDate : comunArchivos.listadoFicherosDate) {
				String pathFichero = Run.pathPorProcesar + File.separator + nombreFicheroDate;
				File ficheroFecha = new File(pathFichero);
				if(ficheroFecha.exists() && ficheroFecha.isDirectory()) {
					String[] nombresArchivos = ficheroFecha.list();
					logger.info("Procesando fichero date: " + nombreFicheroDate);
					for(String nombreArchivo : nombresArchivos) {
						String ficheroArchivoS = Run.pathPorProcesar + File.separator
								+ nombreFicheroDate + File.separator + nombreArchivo;
						File ficheroArchivoF = new File(ficheroArchivoS);
						if(!ficheroArchivoF.isDirectory()) {
							hayArchivoLLamada(nombreArchivo, nombreFicheroDate);
						} else {
							logger.fatal("problemas con el archivo a procesar: " + ficheroArchivoS);
						}
					}
				} else {
					logger.fatal("Archivo fecha no es directorio o no existe");
				}
				
			}
		} else {
			logger.fatal("No hay conexion al servidor...");
		}		
	}
	
	private static void hayArchivoLLamada(String nombreArchivo, String nombreFecha) {

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
					subeArchivoLLamada(nombreArchivo, nombreFecha);
				} else {
					moverArchivo(nombreArchivo, nombreFecha);
				}
			}
			
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			logger.fatal(e.toString());
		}
	}
	
	private static void subeArchivoLLamada(String nombreArchivo, String nombreFecha) {
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
				moverArchivo(nombreArchivo, nombreFecha);
			}
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			logger.fatal(e.toString());
		}
	}
	
	private static void moverArchivo(String nombreArchivo, String nombreFecha) {
		logger.info("Moviendo archivo: " + nombreArchivo);
		String pathOrigenFecha = Run.pathPorProcesar + File.separator + nombreFecha;
		String pathDestFecha = Run.pathProcesados + File.separator + nombreFecha;
		File destFecha = new File(pathDestFecha);
		if(!destFecha.exists()) {
			destFecha.mkdirs();
		}
		try {
			Archivos.fileMove(pathOrigenFecha + File.separator + nombreArchivo,
					pathDestFecha + File.separator +  nombreArchivo);
			File origenFecha = new File(pathOrigenFecha);
			if(origenFecha.list().length == 0) {
				origenFecha.delete();
			}
		} catch (MoverArchivosException e) {
			logger.error("Error moviendo el archivo");
			logger.error(e.getMessage());
		}
	}
}
