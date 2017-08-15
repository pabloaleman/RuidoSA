package com.sertec.exceptions;

public class CalculaPromediosException extends Exception {

	private static final long serialVersionUID = -1420204378815353479L;
	private String codigoError;
	
	public CalculaPromediosException(String message, String codError) {
		super(message);
		this.codigoError = codError;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	
}
