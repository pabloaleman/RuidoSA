package com.sertec.enums;

public enum CatalogoEnum {
	CANAL("CANAL", "canales", "canal"),
	PERFIL("PERFIL", "perfiles", "perfil"),
	MEDIDA("MEDIDA", "medidas", "medida"),
	NIVEL("NIVEL", "niveles", "nivel"),
	PLATAFORMA("PLATAFORMA", "plataformas", "plataforma"),
	TIPO_DATO("TIPO_DATO", "tipos de datos", "tipo de dato");
	
	private final String tipoCatalogo;
	private final String plural;
	private final String nombre; 
	
	private CatalogoEnum(String s, String plura, String nombr) {
		tipoCatalogo = s;
		plural = plura;
		nombre = nombr;
	}
	
	@Override
	public String toString() {
		return tipoCatalogo;
	}
	public String getPlural() {
		return plural;
	}
	public String getNombre() {
		return nombre;
	}

}
