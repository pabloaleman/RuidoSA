package com.sertec.beans;

import java.util.ArrayList;
import java.util.List;

public class CalculaPromediosMensajesListBean {
	private List<String> mensajesError = new ArrayList<>();
	private List<String> mensajesInformacion = new ArrayList<>();
	private List<String> mensajesWarning = new ArrayList<>();
	
	public List<String> getMensajesError() {
		return mensajesError;
	}
	public void setMensajesError(List<String> mensajesError) {
		this.mensajesError = mensajesError;
	}
	public List<String> getMensajesInformacion() {
		return mensajesInformacion;
	}
	public void setMensajesInformacion(List<String> mensajesInformacion) {
		this.mensajesInformacion = mensajesInformacion;
	}
	public List<String> getMensajesWarning() {
		return mensajesWarning;
	}
	public void setMensajesWarning(List<String> mensajesWarning) {
		this.mensajesWarning = mensajesWarning;
	}
	
	

}
