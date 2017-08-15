package com.sertec.enums;

public enum TipoDatoEnum {
	UNA_HORA("1h"),
	PROMEDIO_DIA("pd"),
	PROMEDIO_NOCHE("pn"),
	PROMEDIO_24H("24h");
	private final String tipoDato;
	
	private TipoDatoEnum(String s) {
		tipoDato = s;
	}
	
	@Override
	public String toString() {
		return tipoDato;
	}
}
