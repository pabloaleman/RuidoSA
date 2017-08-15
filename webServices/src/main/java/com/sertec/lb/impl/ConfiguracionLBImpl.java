package com.sertec.lb.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import com.sertec.daos.ConfiguracionDao;
import com.sertec.domain.Configuracion;
import com.sertec.lb.ConfiguracionLB;

@Service
@Named("configuracionLB")
public class ConfiguracionLBImpl implements ConfiguracionLB {

	@Inject
	ConfiguracionDao configuracionDao;
	
	@Override
	public Configuracion getConfiguracionPorItem(String item) {
		return configuracionDao.getConfiguracionPorItem(item);
	}
	@Override
	public List<Configuracion> getAllConfigurations() {
		return configuracionDao.getAllConfigurations();
	}
	@Override
	public void actualizar(Configuracion configuracion) {
		configuracionDao.actualiza(configuracion);
	}
	
	

}
