package com.sertec.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "archivo_binario")
@NamedQueries({
    @NamedQuery(name = "ArchivoBinario.findByNombreAndEstacion", query = "SELECT e FROM ArchivoBinario e WHERE e.nombre = :nombre and e.estacion = :estacion"),
    @NamedQuery(name = "ArchivoBinario.findByEstacion", query = "SELECT e FROM ArchivoBinario e WHERE e.estacion = :estacion")})
public class ArchivoBinario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@SequenceGenerator(name="archivo_binario_seq", sequenceName="archivo_binario_seq",  allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="archivo_binario_seq")
	@NotNull
	@Column(name = "id")
	private Long id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "nombre")
	private String nombre;

	@Column(name = "contenido")
	private byte[] contenido;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "md5")
	private String md5;

	@Basic(optional = false)
	@NotNull
	@Column(name = "fecha_subida")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaSubida;
	
	@JoinColumn(name = "estacion", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Estacion estacion;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaSubida() {
		return fechaSubida;
	}

	public void setFechaSubida(Date fechaSubida) {
		this.fechaSubida = fechaSubida;
	}

	public Estacion getEstacion() {
		return estacion;
	}

	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public byte[] getContenido() {
		return contenido;
	}

	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
	
	
	
}
