package com.sertecimedco.ruido;

import java.io.File;

import org.apache.log4j.Logger;

import com.megasoftworks.gl.util.PropertiesReader;
import com.megasoftworks.gl.util.PropertiesReload;

public class Run {
	
	static Logger logger = Logger.getLogger(Run.class);
	
	private PropertiesReload properties = new PropertiesReload();
	public static String SERVER_IP;
	public static String SERVER_PORT;
	public static String SERVER_SERVICIO;
	public static String SERVICIO_HAY_ARCHIVO;
	public static String SERVICIO_SUBE_ARCHIVO;
	public static String ESTACION;
	public static String pathPorProcesar;
	public static String pathProcesados;
	public String[] listadoArchivos;
	
	public Run() {
		properties = PropertiesReader.propertiesReader("configuracion.properties");
		SERVER_IP = properties.getProperty("SERVER_IP");
		logger.info("SERVER_IP: " + SERVER_IP);
		pathPorProcesar = properties.getProperty("PATH_POR_PROCESAR");
		logger.info("PATH_POR_PROCESAR: " + pathPorProcesar);
		pathProcesados = properties.getProperty("PATH_PROCESADOS");
		logger.info("PATH_PROCESADOS: " + pathProcesados);
		SERVER_PORT = properties.getProperty("SERVER_PORT");
		logger.info("SERVER_PORT: " + SERVER_PORT);
		SERVER_SERVICIO = properties.getProperty("SERVER_SERVICIO");
		SERVICIO_HAY_ARCHIVO = properties.getProperty("SERVICIO_HAY_ARCHIVO");
		SERVICIO_SUBE_ARCHIVO = properties.getProperty("SERVICIO_SUBE_ARCHIVO");
		ESTACION = properties.getProperty("ESTACION");
	}
	
	public boolean obtieneListadoArchivos() {
		boolean ok = true;
		File ficheroPorProcesar = new File(pathPorProcesar);
		if (ficheroPorProcesar.exists() && ficheroPorProcesar.isDirectory()) {
			listadoArchivos = ficheroPorProcesar.list();
		} else {
			logger.fatal("Error al buscar la carpeta de origen de los archivos");
			ok = false;
		}
		return ok;
	}

}
