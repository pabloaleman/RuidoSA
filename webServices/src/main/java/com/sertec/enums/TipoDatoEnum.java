package com.sertec.enums;

public enum TipoDatoEnum {
	UNA_HORA("1h", "1H", "60"),
	PROMEDIO_DIA("pd", "HORAS_PROMEDIO_DIA", "N_DATOS_MINIMO_PROMEDIO_DIA"),
	PROMEDIO_NOCHE("pn", "HORAS_PROMEDIO_NOCHE", "N_DATOS_MINIMO_PROMEDIO_NOCHE"),
	PROMEDIO_24H("24h", "HORAS_DIARIO", "N_DATOS_MINIMO_DIARIO");
	private final String tipoDato;
	private final String tipoDatoConf;
	private final String nDatosMinimoConf;
	
	private TipoDatoEnum(String s, String co, String nD) {
		tipoDato = s;
		tipoDatoConf = co;
		nDatosMinimoConf = nD;
	}
	
	public String getTipoDato() {
		return tipoDato;
	}

	public String getTipoDatoConf() {
		return tipoDatoConf;
	}
	public String getNDatosMinimoConf() {
		return nDatosMinimoConf;
	}

	@Override
	public String toString() {
		return tipoDato;
	}
	
}
