package com.sertec.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.model.chart.LineChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.megasoftworks.gl.manage.Fechas;
import com.megasoftworks.jsfUtil.MessageUtils;
import com.megasoftworks.jsfUtil.enums.SeverityMessageEnum;
import com.sertec.domain.Catalogo;
import com.sertec.domain.Dato;
import com.sertec.domain.DatoCalculado;
import com.sertec.domain.Estacion;
import com.sertec.domain.Parametro;
import com.sertec.enums.GeneraReportesErrorEnum;
import com.sertec.exceptions.GeneraReporteException;
import com.sertec.lb.ReporteServicio;

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
	private List<Catalogo> tipoDatosSelecionados;
	private List<Dato> datos;
	private List<DatoCalculado> promedios;
	private LineChartModel modeloValores = new LineChartModel();
	
	@Autowired
	private ReporteServicio reporteServicio;
	
	public void procesarReporte() {
		fechaFin = Fechas.finHoMiSe(fechaFin);
		LOGGER.info("Buscando datos");
		LOGGER.info("estaciones: " + estacionesSeleccionadas);
		LOGGER.info("Fecha Inicio: " + fechaInicio);
		LOGGER.info("Fecha fin: " + fechaFin);
		LOGGER.info("Parametros: " + parametrosSeleccionados);
		fechaInicio = Fechas.enceraHoMiSe(fechaInicio);
		fechaFin = Fechas.enceraHoMiSe(fechaFin);
		try {
			datos = reporteServicio.getListDatos(fechaInicio, fechaFin, parametrosSeleccionados, estacionesSeleccionadas);
		} catch (GeneraReporteException e) {
			if(e.getEnumError() == GeneraReportesErrorEnum.FECHA_INICIO_MAYOR_FECHA_FIN) {
				String mensaje = ResourceBundle.getBundle("mensajes").getString("fechaInicioMenorFechaFin");
				MessageUtils.showMessage(SeverityMessageEnum.ERROR, "Error", mensaje);
			}
		}
		
	}
	
	public void procesarReportePromedios() {
		fechaFin = Fechas.finHoMiSe(fechaFin);
		LOGGER.info("Buscando datos");
		LOGGER.info("estaciones: " + estacionesSeleccionadas);
		LOGGER.info("Fecha Inicio: " + fechaInicio);
		LOGGER.info("Fecha fin: " + fechaFin);
		LOGGER.info("Parametros: " + parametrosSeleccionados);
		fechaInicio = Fechas.enceraHoMiSe(fechaInicio);
		fechaFin = Fechas.enceraHoMiSe(fechaFin);
		try {
			promedios = reporteServicio.getListPromedios(fechaInicio, fechaFin, parametrosSeleccionados, estacionesSeleccionadas, tipoDatosSelecionados);
		} catch (GeneraReporteException e) {
			if(e.getEnumError() == GeneraReportesErrorEnum.FECHA_INICIO_MAYOR_FECHA_FIN) {
				String mensaje = ResourceBundle.getBundle("mensajes").getString("fechaInicioMenorFechaFin");
				MessageUtils.showMessage(SeverityMessageEnum.ERROR, "Error", mensaje);
			}
		}
		
	}
	
	public void procesaGrafico() {
		fechaFin = Fechas.finHoMiSe(fechaFin);
		LOGGER.info("Buscando datos");
		LOGGER.info("estaciones: " + estacionesSeleccionadas);
		LOGGER.info("Fecha Inicio: " + fechaInicio);
		LOGGER.info("Fecha fin: " + fechaFin);
		LOGGER.info("Parametros: " + parametrosSeleccionados);
		fechaInicio = Fechas.enceraHoMiSe(fechaInicio);
		fechaFin = Fechas.enceraHoMiSe(fechaFin);
		try {
			modeloValores = reporteServicio.graficarFindAllByFechasAndEstacionAndContaminante(fechaInicio, fechaFin, parametrosSeleccionados, estacionesSeleccionadas);
		} catch (GeneraReporteException e) {
			if(e.getEnumError() == GeneraReportesErrorEnum.FECHA_INICIO_MAYOR_FECHA_FIN) {
				String mensaje = ResourceBundle.getBundle("mensajes").getString("fechaInicioMenorFechaFin");
				MessageUtils.showMessage(SeverityMessageEnum.ERROR, "Error", mensaje);
			}
		}
	}
	
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

	public LineChartModel getModeloValores() {
		return modeloValores;
	}
	public void setModeloValores(LineChartModel modeloValores) {
		this.modeloValores = modeloValores;
	}
	public List<DatoCalculado> getPromedios() {
		return promedios;
	}
	public void setPromedios(List<DatoCalculado> promedios) {
		this.promedios = promedios;
	}

	public List<Catalogo> getTipoDatosSelecionados() {
		return tipoDatosSelecionados;
	}
	public void setTipoDatosSelecionados(List<Catalogo> tipoDatosSelecionados) {
		this.tipoDatosSelecionados = tipoDatosSelecionados;
	}
}
