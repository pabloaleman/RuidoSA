package com.sertecimedco.ruido;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.megasoftworks.gl.manage.Archivos;
import com.megasoftworks.gl.util.PropertiesReader;
import com.megasoftworks.gl.util.PropertiesReload;

public class Run {
	
	static Logger logger = Logger.getLogger(Run.class);
	
	private static PropertiesReload properties = new PropertiesReload();
	public static String FRECUENCIA;
	public static String SERVER_IP;
	public static String SERVER_PORT;
	public static String SERVER_SERVICIO;
	public static String SERVICIO_HAY_ARCHIVO;
	public static String SERVICIO_SUBE_ARCHIVO;
	public static String ESTACION;
	public static String pathPorProcesar;
	public static String pathProcesados;
	public static String FILE_HISTORICOS;
	
	public static List<String> MAPA_ARCHIVOS_SUBIDOS = new ArrayList<>();
	
	
	public static void cargaParametros() {
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
		FRECUENCIA = properties.getProperty("FRECUENCIA");
		FILE_HISTORICOS = properties.getProperty("FILE_HISTORICOS");
		cargaArchivosSubidosDeArchivo();
		logger.info("FRECUENCIA: " + FRECUENCIA);
	}
	
	public static void cargaArchivosSubidosDeArchivo() {
		String archivosSubidosArchivo = Archivos.leerArchivo(FILE_HISTORICOS);
		String[] archivosSubidosArray = archivosSubidosArchivo.split("\n");
		MAPA_ARCHIVOS_SUBIDOS = Arrays.asList(archivosSubidosArray);
	}
}
