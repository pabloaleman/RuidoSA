package com.sertec.ws;

import java.io.File;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.megasoftworks.gl.util.MD5;
import com.sertec.domain.ArchivoBinario;
import com.sertec.domain.Configuracion;
import com.sertec.lb.ArchivoBinarioLB;
import com.sertec.lb.ConfiguracionLB;
import com.sertecimedco.ruido.clasesComunes.HayArchivoResponse;
import com.sertecimedco.ruido.clasesComunes.SubeArchivoRequest;
import com.sertecimedco.ruido.clasesComunes.SubeArchivoResponse;

@RestController
@Controller
@RequestMapping("/ruidoWS")
public class ArchivosWs {

	private final static Logger LOGGER = Logger.getLogger(ArchivosWs.class);

	@Autowired
	ArchivoBinarioLB archivoBinarioLB;
	
	@Autowired
	ConfiguracionLB configuracionLB;

	@RequestMapping(value = "hayArchivo/{nombreArchivo}/{estacion}", method = RequestMethod.POST)
	public @ResponseBody HayArchivoResponse hayArchivo(@PathVariable("nombreArchivo") String nombreArchivo,
			@PathVariable("estacion") String estacion) {

		LOGGER.debug("WS HA Nombre recibido: " + nombreArchivo);
		LOGGER.debug("WS HA Nombre estacion: " + estacion);
		HayArchivoResponse retorno = new HayArchivoResponse();
		retorno.setHayArchivo(false);
		if (!archivoBinarioLB.hayArchivo(nombreArchivo, estacion)) {
			LOGGER.debug("WS HA No hay archivo");
		} else {
			retorno.setHayArchivo(true);
			LOGGER.debug("WS HA Hay archivo");
		}
		retorno.setMensaje("Mensaje hay archivo");
		LOGGER.debug("Hay archivo: " + retorno);
		return retorno;

	}

	@RequestMapping(value = "subeArchivo", method = RequestMethod.POST)
	public @ResponseBody SubeArchivoResponse subeArchivo( @RequestBody SubeArchivoRequest archivoRequest) {

		LOGGER.debug("WS SA Nombre recibido: " + archivoRequest.getNombreArchivo());
		LOGGER.debug("WS SA Md5: " + archivoRequest.getMd5());
		LOGGER.debug("WS SA Nombre estacion: " + archivoRequest.getEstacion());

		SubeArchivoResponse retorno = new SubeArchivoResponse();
		retorno.setProblema(false);

//		if (!archivoBinarioLB.hayArchivo(archivoRequest.getNombreArchivo(),
//				archivoRequest.getEstacion())) {

		Configuracion configPath = configuracionLB.getConfiguracionPorItem("PATH_LOCAL");
			String path = configPath.getValor() + File.separator + archivoRequest.getEstacion();
			try {
				if(!(new File(path).exists())) {
					LOGGER.warn("Se va a crear el directorio: " + path);
					new File(path).mkdirs();
					
				}
				String pathArchivo = path + File.separator + archivoRequest.getNombreArchivo();
				//Se escribe el archivo en el disco
				FileUtils.writeByteArrayToFile(new File(pathArchivo), archivoRequest.getContenido());
				File archivo = new File(pathArchivo);
				String md5 = MD5.fileMd5Sum(archivo);
				if(md5.equals(archivoRequest.getMd5())) {
					ArchivoBinario ab = new ArchivoBinario();
					ab.setFechaSubida(new Date());
					ab.setNombre(archivoRequest.getNombreArchivo());
					ab.setContenido(archivoRequest.getContenido());
					ab.setMd5(md5);
					retorno.setProblema(!archivoBinarioLB.save(ab, archivoRequest.getEstacion()));
					if(!retorno.isProblema()) {
						retorno.setMensaje("File uploaded successfully!");
					} else {
						LOGGER.error("Problema al tratar de guardar el archivo");
					}
				} else {
					LOGGER.error("No coinciden los md5");
					retorno.setProblema(true);
				}
				
				
			} catch(Exception e) {
				LOGGER.error("Hay errores");
				LOGGER.error(e.getMessage());
				retorno.setProblema(true);
			}
//		}
		return retorno;
	}

	public void algo() {// Get name of uploaded file.

	}

}