package com.sertec.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "archivo_datos")
public class ArchivoDatos implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@SequenceGenerator(name="archivo_datos_seq", sequenceName="archivo_datos_seq",  allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="archivo_datos_seq")
	@Column(name = "id")
	private Long id;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "nombre")
	private String nombre;

	@Basic(optional = false)
	@NotNull
	@Column(name = "fecha_carga")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaSubida;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "estado")
	private String estado = "Cargado";
	
	@Column(name = "estado")
	@NotNull
	private byte[] contenidoArchivo;

	@JoinColumn(name = "estacion", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Estacion estacion;
	
	@JoinColumn(name = "usuario", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Estacion usuario;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "archivoDatos")
    private Collection<DatoHorario> datosHorarios;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "archivoDatos")
    private Collection<Promedio> promedios;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "archivoDatos")
    private Collection<MaximoMinimo> maxMins;

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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Estacion getEstacion() {
		return estacion;
	}

	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

	public Estacion getUsuario() {
		return usuario;
	}

	public void setUsuario(Estacion usuario) {
		this.usuario = usuario;
	}

	public Collection<DatoHorario> getDatosHorarios() {
		return datosHorarios;
	}

	public void setDatosHorarios(Collection<DatoHorario> datosHorarios) {
		this.datosHorarios = datosHorarios;
	}

	public Collection<Promedio> getPromedios() {
		return promedios;
	}

	public void setPromedios(Collection<Promedio> promedios) {
		this.promedios = promedios;
	}

	public Collection<MaximoMinimo> getMaxMins() {
		return maxMins;
	}

	public void setMaxMins(Collection<MaximoMinimo> maxMins) {
		this.maxMins = maxMins;
	}

	public byte[] getContenidoArchivo() {
		return contenidoArchivo;
	}

	public void setContenidoArchivo(byte[] contenidoArchivo) {
		this.contenidoArchivo = contenidoArchivo;
	}
	
	

	
}
