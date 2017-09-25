package com.sertecimedco.ruido.clasesComunes;

public class SubeArchivoRequest {
	private String nombreArchivo;
	private String nombreFecha;
	private String md5;
	private String estacion;
	private byte[] contenido;
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getNombreFecha() {
		return nombreFecha;
	}
	public void setNombreFecha(String nombreFecha) {
		this.nombreFecha = nombreFecha;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getEstacion() {
		return estacion;
	}
	public void setEstacion(String estacion) {
		this.estacion = estacion;
	}
	public byte[] getContenido() {
		return contenido;
	}
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
	
	@Override
	public String toString() {
		return "nombreArchivo: " + nombreArchivo
				+ " nombreFecha: " + nombreFecha
				+ " md5: " + md5
				+ " estacion: " + estacion;
	}
	
	
}
