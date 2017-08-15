package com.sertec.exceptions;

import com.sertec.domain.Parametro;

public class ExisteParametroException extends Exception {

	private static final long serialVersionUID = -4509806763154471709L;
	private Parametro parametro;
	
	public ExisteParametroException(String message, Parametro parametro) {
		super(message);
		this.parametro = parametro;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}
	
	

}
