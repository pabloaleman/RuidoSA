package com.sertec.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
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
	private Date fechaSubida = new Date();
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "estado")
	private String estado = "Cargado";

	@JoinColumn(name = "estacion")
    @ManyToOne(optional = false)
	private Estacion estacion;
	
	@JoinColumn(name = "usuario")
    @ManyToOne(optional = false)
	private Usuario usuario;
	
	@JoinColumn(name = "plataforma")
    @ManyToOne(optional = false)
	private Catalogo plataforma;
	
	@OneToMany(mappedBy = "archivoDatos")
    private Collection<Dato> datos;

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Catalogo getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(Catalogo plataforma) {
		this.plataforma = plataforma;
	}

	public Collection<Dato> getDatos() {
		return datos;
	}

	public void setDatos(Collection<Dato> datos) {
		this.datos = datos;
	}
	
	
}
