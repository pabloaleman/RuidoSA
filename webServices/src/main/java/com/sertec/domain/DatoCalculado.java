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
@Table(name = "dato_calculado")
@NamedQueries({
	@NamedQuery(name="DatoCalculado.getDatoByEstacionDAndTipoDatoAndFechas", query="SELECT d from DatoCalculado d where d.estado = 'Activo' and d.estacion = :estacion and d.tipoDato = :tipoDato and d.fechaD between :fechaI and :fechaF"),
	@NamedQuery(name="DatoCalculado.getDatosByEstacionesAndParametrosAndFechas", query = "SELECT d FROM DatoCalculado d where d.estado = 'Activo' and d.estacion in :estaciones and d.parametro in :parametros and d.fecha between :fInicio and :fFin order by d.estacion, d.parametro, d.fecha"),
	@NamedQuery(name="DatoCalculado.getDatosByEstacionesAndParametrosAndTiposDatoAndFechas", query = "SELECT d FROM DatoCalculado d where d.estado = 'Activo' and d.tipoDato in :tiposDato and d.estacion in :estaciones and d.parametro in :parametros and d.fecha between :fInicio and :fFin order by d.estacion, d.parametro, d.fecha")
})
public class DatoCalculado  implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@SequenceGenerator(name="dato_calculado_seq", sequenceName="dato_calculado_seq",  allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="dato_calculado_seq")
	@Column(name = "id")
	private Long id;

	@Basic(optional = false)
	@NotNull
	@Column(name = "fecha")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "fechaD")
	private Long fechaD;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "promedio")
	private Double promedio;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "maximo")
	private Double maximo = Double.MIN_VALUE;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "fecha_maximo")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaMaximo;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "minimo")
	private Double minimo = Double.MAX_VALUE;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "fecha_minimo")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaMinimo;
	
	@Column(name = "estado")
	private String estado = "Activo";
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "fecha_calculo")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCalculo;
	
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

	public Long getFechaD() {
		return fechaD;
	}

	public void setFechaD(Long fechaD) {
		this.fechaD = fechaD;
	}

	public Double getPromedio() {
		return promedio;
	}

	public void setPromedio(Double promedio) {
		this.promedio = promedio;
	}

	public Double getMaximo() {
		return maximo;
	}

	public void setMaximo(Double maximo) {
		this.maximo = maximo;
	}

	public Date getFechaMaximo() {
		return fechaMaximo;
	}

	public void setFechaMaximo(Date fechaMaximo) {
		this.fechaMaximo = fechaMaximo;
	}

	public Double getMinimo() {
		return minimo;
	}

	public void setMinimo(Double minimo) {
		this.minimo = minimo;
	}

	public Date getFechaMinimo() {
		return fechaMinimo;
	}

	public void setFechaMinimo(Date fechaMinimo) {
		this.fechaMinimo = fechaMinimo;
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

	public Date getFechaCalculo() {
		return fechaCalculo;
	}

	public void setFechaCalculo(Date fechaCalculo) {
		this.fechaCalculo = fechaCalculo;
	}
	

}
