package com.sertec.exceptions;

import com.sertec.enums.CatalogoEnum;

public class NoCatalogosException extends Exception {

	private static final long serialVersionUID = -4509806763154471709L;
	
	private CatalogoEnum catalogo;
	
	public NoCatalogosException(String message, CatalogoEnum catalogo) {
		super(message);
		this.catalogo = catalogo;
	}

	public CatalogoEnum getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(CatalogoEnum catalogoEnum) {
		this.catalogo = catalogoEnum;
	}
	
	
}
