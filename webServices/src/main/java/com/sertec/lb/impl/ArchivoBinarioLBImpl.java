package com.sertec.lb.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.sertec.daos.ArchivoBinarioDao;
import com.sertec.daos.EstacionDao;
import com.sertec.domain.ArchivoBinario;
import com.sertec.domain.Estacion;
import com.sertec.lb.ArchivoBinarioLB;

@Component
@Named("archivoBinarioLB")
public class ArchivoBinarioLBImpl implements ArchivoBinarioLB {
	
	private final static Logger LOGGER = Logger.getLogger(ArchivoBinarioLBImpl.class);
	
	@PostConstruct
	private void init() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Autowired
	ArchivoBinarioDao archivoBinarioDao;
	@Autowired
	EstacionDao estacionDao;
	
	public boolean hayArchivo(String nombreArchivo, String acronimo) {
		LOGGER.debug("llama a la funcion lb hay archivo");
		Estacion e = estacionDao.findEstacionByAcronimo(acronimo);
		if (e== null) {
			LOGGER.error("No hay estación con acronimo: " + acronimo + " en el sistema");
			//return false;
		//} else {
		}
			return archivoBinarioDao.hayArchivo(nombreArchivo, e);
		//}
	}
	
	public boolean save(ArchivoBinario file, String estacion) {
		LOGGER.debug("Se va a guardar el archivo binario en bdd");
		Estacion e = estacionDao.findEstacionByAcronimo(estacion);
		if(e == null) {
			LOGGER.error("No hay estacion con acronimo: " + estacion);
			return false;
		} else {
			LOGGER.debug("Estacion asignada: " + e.getNombre());
			file.setEstacion(e);
			return archivoBinarioDao.save(file);
		}
	}

	@Override
	public Map<String, String> retornoHistoricos(String estacion) {
		Estacion e = estacionDao.findEstacionByAcronimo(estacion);
		Map<String, String> retorno = new HashMap<>();
		List<ArchivoBinario> archivos = archivoBinarioDao.getArchivos(e);
		for(ArchivoBinario archivoBinario : archivos) {
			retorno.put(archivoBinario.getNombre(), archivoBinario.getNombre());
		}
		return retorno;
	}

}
