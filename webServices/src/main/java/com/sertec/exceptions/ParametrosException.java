package com.sertec.exceptions;

public class ParametrosException extends Exception {

	private static final long serialVersionUID = 6217752273257959368L;
	
	private String errorCode;
	
	public ParametrosException(String message, String errorCod) {
		super(message);
		this.errorCode = errorCod;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
