package com.sertec.beans;

import java.util.Date;

public class PromedioBean {
	private double promedio;
	private double maximo = Double.MIN_VALUE;
	private Date horaMaximo;
	private Date horaMinimo;
	private double minimo = Double.MAX_VALUE;
	public double getPromedio() {
		return promedio;
	}
	public void setPromedio(double promedio) {
		this.promedio = promedio;
	}
	public double getMaximo() {
		return maximo;
	}
	public void setMaximo(double maximo) {
		this.maximo = maximo;
	}
	public double getMinimo() {
		return minimo;
	}
	public void setMinimo(double minimo) {
		this.minimo = minimo;
	}
	public Date getHoraMaximo() {
		return horaMaximo;
	}
	public void setHoraMaximo(Date horaMaximo) {
		this.horaMaximo = horaMaximo;
	}
	public Date getHoraMinimo() {
		return horaMinimo;
	}
	public void setHoraMinimo(Date horaMinimo) {
		this.horaMinimo = horaMinimo;
	}
	
	
	
}
