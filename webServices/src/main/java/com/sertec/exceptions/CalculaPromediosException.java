package com.sertec.exceptions;

import com.sertec.enums.CalculaPromedioErrorEnum;

public class CalculaPromediosException extends Exception {

	private static final long serialVersionUID = -1420204378815353479L;
	private CalculaPromedioErrorEnum codigoError;
	
	public CalculaPromediosException(String message, CalculaPromedioErrorEnum codError) {
		super(message);
		this.codigoError = codError;
	}

	public CalculaPromedioErrorEnum getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(CalculaPromedioErrorEnum codigoError) {
		this.codigoError = codigoError;
	}

	
}
