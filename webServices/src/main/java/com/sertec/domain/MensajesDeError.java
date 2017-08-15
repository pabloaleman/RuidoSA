package com.sertec.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "mensajes")
@NamedQueries({
    @NamedQuery(name = "Mensajes.findMensajes", query = "SELECT m FROM MensajesDeError m"),
    @NamedQuery(name = "Mensajes.findMensajesByAcronimo", query = "SELECT m FROM MensajesDeError m WHERE m.acronimo = :acronimo")})
public class MensajesDeError implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "id")
	private Long id;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "acronimo")
	private String acronimo;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "descripcion")
	private String descripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAcronimo() {
		return acronimo;
	}

	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
