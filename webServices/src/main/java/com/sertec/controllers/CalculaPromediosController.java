package com.sertec.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.megasoftworks.jsfUtil.MessageUtils;
import com.megasoftworks.jsfUtil.Redireccion;
import com.megasoftworks.jsfUtil.enums.SeverityMessageEnum;
import com.sertec.beans.CalculaPromediosBean;
import com.sertec.domain.Parametro;
import com.sertec.exceptions.CalculaPromediosException;
import com.sertec.exceptions.ParametrosException;
import com.sertec.lb.CalculaPromediosService;
import com.sertec.lb.ParametroServicio;

@ManagedBean
@Named("calculaPromediosController")
@Scope("session")
public class CalculaPromediosController implements Serializable {
	
	@Autowired
	CalculaPromediosService calculaPromediosService;
	@Autowired
	EstacionController estacionController;
	@Autowired
	ParametroServicio parametroServicio;
	@Autowired
	ParametroController parametroController;

	private static final long serialVersionUID = -5257723025080571069L;
	private Date fechaInicio;
	private Date fechaFin;
	private List<Parametro> parametrosCP;
	
	public void goCalculaPromedios() {
		try {
			parametrosCP = parametroServicio.getParametrosPromedio();
			Redireccion.redirecciona("/ruido/secured/calculaPromedios/calculaPromedios.xhtml");
		} catch (ParametrosException e) {
			String mensaje = ResourceBundle.getBundle("mensajes").getString("noParametros");
			MessageUtils.showMessage(SeverityMessageEnum.WARN, "Advertencia", mensaje);
			parametroController.adminParametros();
		}
	}
	
	
	public void calculaPromedios() {
		try {
			CalculaPromediosBean calculaPromediosBean = calculaPromediosService.calculaPromedios(fechaInicio, fechaFin, estacionController.getEstacionSeleccionada(), parametrosCP);
			if(!calculaPromediosBean.getMensajesError().isEmpty()) {
				MessageUtils.showMessages(calculaPromediosBean.getMensajesError(), SeverityMessageEnum.ERROR);
			}
			
			if(!calculaPromediosBean.getMensajesWarning().isEmpty()) {
				MessageUtils.showMessages(calculaPromediosBean.getMensajesWarning(), SeverityMessageEnum.WARN);
			}
			
			if(!calculaPromediosBean.getMensajesInformacion().isEmpty()) {
				MessageUtils.showMessages(calculaPromediosBean.getMensajesInformacion(), SeverityMessageEnum.INFO);
			}
		} catch (CalculaPromediosException e) {
			String mensaje = ResourceBundle.getBundle("errorMensajes").getString(e.getCodigoError());
			MessageUtils.showMessage(SeverityMessageEnum.ERROR, "Error", mensaje);
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

	public EstacionController getEstacionController() {
		return estacionController;
	}

	public void setEstacionController(EstacionController estacionController) {
		this.estacionController = estacionController;
	}
	
	

}
