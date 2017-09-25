package com.sertec.exceptions;

import com.sertec.enums.GeneraReportesErrorEnum;

public class GeneraReporteException extends Exception {
	private static final long serialVersionUID = 2541967093515970754L;
	private GeneraReportesErrorEnum enumError;
	public GeneraReporteException(String message, GeneraReportesErrorEnum enumErro) {
		super(message);
		this.enumError = enumErro;
	}
	public GeneraReportesErrorEnum getEnumError() {
		return enumError;
	}
	public void setEnumError(GeneraReportesErrorEnum enumError) {
		this.enumError = enumError;
	}
}
