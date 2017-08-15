package com.sertec.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.sertec.domain.Catalogo;
import com.sertec.domain.Dato;
import com.sertec.domain.Estacion;
import com.sertec.domain.Parametro;

@ManagedBean
@Named("reporteController")
@Scope("session")
public class ReporteController   implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ReporteController.class);
	private Date fechaInicio;
	private Date fechaFin;
	private List<Parametro> parametrosSeleccionados;
	private List<Estacion> estacionesSeleccionadas;
	private List<Dato> datos;
	private Catalogo tipoDato;
	
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public List<Parametro> getParametrosSeleccionados() {
		return parametrosSeleccionados;
	}
	public void setParametrosSeleccionados(List<Parametro> parametrosSeleccionados) {
		this.parametrosSeleccionados = parametrosSeleccionados;
	}
	public List<Estacion> getEstacionesSeleccionadas() {
		return estacionesSeleccionadas;
	}
	public void setEstacionesSeleccionadas(List<Estacion> estacionesSeleccionadas) {
		this.estacionesSeleccionadas = estacionesSeleccionadas;
	}
	public List<Dato> getDatos() {
		return datos;
	}
	public void setDatos(List<Dato> datos) {
		this.datos = datos;
	}
	public Catalogo getTipoDato() {
		return tipoDato;
	}
	public void setTipoDato(Catalogo tipoDato) {
		this.tipoDato = tipoDato;
	}
}
