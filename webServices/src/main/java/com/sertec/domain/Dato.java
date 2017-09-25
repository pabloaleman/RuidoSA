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

@Entity
@Table(name = "dato")
@NamedQueries({
	@NamedQuery(name = "Dato.findDatos", query = "SELECT d FROM Dato d where d.estado = 'Activo' and d.parametro = :parametro and d.tipoDato = :tipoDato and d.fecha between :fInicio and :fFin"),
	@NamedQuery(name = "Dato.findDatosParaPromedio", query = "SELECT d FROM Dato d where d.estado = 'Activo' and d.estacion = :estacion and d.parametro = :parametro and d.tipoDato = :tipoDato and d.hora in :horas and d.fecha between :fInicio and :fFin"),
	@NamedQuery(name = "Dato.getDatosByEstacionesAndParametrosAndFechas", query = "SELECT d FROM Dato d where d.estado = 'Activo' and d.estacion in :estaciones and d.parametro in :parametros and d.fecha between :fInicio and :fFin order by d.estacion, d.parametro, d.fecha"),
	@NamedQuery(name = "Dato.getDatosByEstacionAndParametroAndFechas", query = "SELECT d FROM Dato d where d.estado = 'Activo' and d.estacion = :estacion and d.parametro = :parametro and d.fecha between :fInicio and :fFin order by d.fecha"),
    @NamedQuery(name = "Dato.findDato", query = "SELECT d FROM Dato d where d.estado = 'Activo' and d.parametro = :parametro and d.tipoDato = :tipoDato and d.fechaD = :fechaD")})

public class Dato implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@SequenceGenerator(name="dato_seq", sequenceName="dato_seq",  allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="dato_seq")
	@Column(name = "id")
	private Long id;

	@Basic(optional = false)
	@NotNull
	@Column(name = "fecha")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "fecha_carga")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCarga;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "fechaD")
	private Long fechaD;	
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "hora")
	private Integer hora;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "valor")
	private Double valor;
	
	@Column(name = "estado")
	private String estado = "Activo";
	
	@JoinColumn(name = "archivoDatos", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private ArchivoDatos archivoDatos;
	
	@JoinColumn(name = "estacion", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Estacion estacion;
	
	@JoinColumn(name = "usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Usuario usuario;
	
	@JoinColumn(name = "parametro", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Parametro parametro;

	@JoinColumn(name = "tipoDato", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Catalogo tipoDato;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getHora() {
		return hora;
	}

	public void setHora(Integer hora) {
		this.hora = hora;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public ArchivoDatos getArchivoDatos() {
		return archivoDatos;
	}

	public void setArchivoDatos(ArchivoDatos archivoDatos) {
		this.archivoDatos = archivoDatos;
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

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public Catalogo getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(Catalogo tipoDato) {
		this.tipoDato = tipoDato;
	}

	public Long getFechaD() {
		return fechaD;
	}

	public void setFechaD(Long fechaD) {
		this.fechaD = fechaD;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	
	

}
