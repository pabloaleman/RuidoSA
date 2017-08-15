package com.sertec.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.sertec.domain.Estacion;
import com.sertec.lb.EstacionServicio;

@ManagedBean
@Named("estacionController")
@Scope("session")
public class EstacionController implements Serializable {
	
	private static final long serialVersionUID = 8130774970331376606L;
	private List<Estacion> estacionesActivas;
	private Estacion estacionSeleccionada;
	//private Long idEstacion;
	
	@Autowired
	EstacionServicio estacionServicio;
	
	@PostConstruct
	private void init() {
		estacionesActivas = estacionServicio.getEstacionesActivas();
	}

	public List<Estacion> getEstacionesActivas() {
		return estacionesActivas;
	}

	public void setEstacionesActivas(List<Estacion> estacionesActivas) {
		this.estacionesActivas = estacionesActivas;
	}

	public Estacion getEstacionSeleccionada() {
		return estacionSeleccionada;
	}

	public void setEstacionSeleccionada(Estacion estacionSeleccionada) {
		this.estacionSeleccionada = estacionSeleccionada;
	}

	/*public Long getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
	}*/
	
	
	
}
