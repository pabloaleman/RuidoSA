package com.sertec.controllers;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import com.sertec.domain.Configuracion;

import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@ManagedBean
@Named("configuracionController")
public class ConfiguracionController {
	List<Configuracion> configuraciones;

	public List<Configuracion> getConfiguraciones() {
		return configuraciones;
	}

	public void setConfiguraciones(List<Configuracion> configuraciones) {
		this.configuraciones = configuraciones;
	}
	
	
}
