package com.sertec.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.sertec.domain.Parametro;
import com.sertec.lb.ParametroServicio;

@ManagedBean
@Named("parametrosReporteController")
@Scope("request")
public class ParametrosReporteController  implements Serializable {

	private static final long serialVersionUID = -7802130883829355230L;
	
	List<Parametro> parametros;
	
	@Autowired
	ParametroServicio parametroServicio;
	
	@PostConstruct
	private void init() {
		parametros = parametroServicio.getParametros();
	}

	public List<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}
	
}
