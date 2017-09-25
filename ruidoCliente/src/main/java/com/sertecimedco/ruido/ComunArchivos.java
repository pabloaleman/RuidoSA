package com.sertecimedco.ruido;

import java.io.File;

import org.apache.log4j.Logger;

public class ComunArchivos {
	static Logger logger = Logger.getLogger(ComunArchivos.class);
	
	public String[] listadoFicherosDate;
	
	public boolean obtieneListadoArchivos() {
		boolean ok = true;
		File ficheroPorProcesar = new File(Run.pathPorProcesar);
		if (ficheroPorProcesar.exists() && ficheroPorProcesar.isDirectory()) {
			listadoFicherosDate = ficheroPorProcesar.list();
		} else {
			logger.fatal("Error al buscar la carpeta de origen de los archivos");
			ok = false;
		}
		return ok;
	}

}
