package com.sertec.beans;

import java.util.ArrayList;
import java.util.List;

import com.sertec.domain.Dato;
import com.sertec.domain.Parametro;

public class PreprocesoArchivoBean {
	private List<Dato> datos = new ArrayList<>();
	private List<Dato> datosPrincipales = new ArrayList<>();
	private List<Dato> datosPersistir = new ArrayList<>();
	private List<Parametro> parametroscargar = new ArrayList<>();
	private List<Parametro> parametrosNoBdd = new ArrayList<>();
	public List<Dato> getDatos() {
		return datos;
	}
	public void setDatos(List<Dato> datos) {
		this.datos = datos;
	}
	public List<Dato> getDatosPrincipales() {
		return datosPrincipales;
	}
	public void setDatosPrincipales(List<Dato> datosPrincipales) {
		this.datosPrincipales = datosPrincipales;
	}
	public List<Parametro> getParametroscargar() {
		return parametroscargar;
	}
	public void setParametroscargar(List<Parametro> parametroscargar) {
		this.parametroscargar = parametroscargar;
	}
	public List<Parametro> getParametrosNoBdd() {
		return parametrosNoBdd;
	}
	public void setParametrosNoBdd(List<Parametro> parametrosNoBdd) {
		this.parametrosNoBdd = parametrosNoBdd;
	}
	public List<Dato> getDatosPersistir() {
		return datosPersistir;
	}
	public void setDatosPersistir(List<Dato> datosPersistir) {
		this.datosPersistir = datosPersistir;
	}
	
	
}
