package com.sertec.domain;

import java.io.Serializable;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.sertec.enums.AccionesEnum;

@Entity
@Table(name = "parametro")
@NamedQueries({
	@NamedQuery(name = "Parametro.getByCanalAndMedidaAndPerfilAndNivel", query = "SELECT p FROM Parametro p where p.canal = :canal and p.medida = :medida and p.perfil = :perfil and p.nivel = :nivel"),
	@NamedQuery(name = "Parametro.getParametros", query = "SELECT p FROM Parametro p"),
    @NamedQuery(name = "Parametro.getParametrosCalculaPromedios", query = "SELECT p FROM Parametro p where p.calculaPromedios = true")})
public class Parametro implements Serializable {

	private static final long serialVersionUID = -5373557947427309822L;
	
	@Id
	@Basic(optional = false)
	@NotNull
	@SequenceGenerator(name="parametro_seq", sequenceName="parametro_seq",  allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="parametro_seq")
	@Column(name = "id")
	private Long id;

	@Basic(optional = false)
	@NotNull
	@Column(name = "descripcion")
	private String descripcion;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "acronimo")
	private String acronimo;
	
	@Column(name = "aplica_promedio")
	private boolean aplicaPromedio = true;
	
	@Column(name = "principal")
	private boolean principal = false;
	
	@Column(name = "guarda")
	private boolean guarda = true;
	
	@Column(name = "calcula_promedios")
	private boolean calculaPromedios = false;
	
	@JoinColumn(name = "canal")
    @ManyToOne(optional = false)
	private Catalogo canal;
	
	@JoinColumn(name = "perfil")
    @ManyToOne(optional = false)
	private Catalogo perfil;
	
	@JoinColumn(name = "medida")
    @ManyToOne(optional = false)
	private Catalogo medida;
	
	@JoinColumn(name = "nivel")
    @ManyToOne
	private Catalogo nivel;
	
	@Transient
	private AccionesEnum accion = AccionesEnum.NONE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAcronimo() {
		return acronimo;
	}

	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}

	public boolean isAplicaPromedio() {
		return aplicaPromedio;
	}

	public void setAplicaPromedio(boolean aplicaPromedio) {
		this.aplicaPromedio = aplicaPromedio;
	}
	
	

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public boolean isGuarda() {
		return guarda;
	}

	public void setGuarda(boolean guarda) {
		this.guarda = guarda;
	}

	public Catalogo getCanal() {
		return canal;
	}

	public void setCanal(Catalogo canal) {
		this.canal = canal;
	}

	public Catalogo getPerfil() {
		return perfil;
	}

	public void setPerfil(Catalogo perfil) {
		this.perfil = perfil;
	}

	public Catalogo getMedida() {
		return medida;
	}
	
	
	
	

	public boolean isCalculaPromedios() {
		return calculaPromedios;
	}

	public void setCalculaPromedios(boolean calculaPromedios) {
		this.calculaPromedios = calculaPromedios;
	}

	public AccionesEnum getAccion() {
		return accion;
	}

	public void setAccion(AccionesEnum accion) {
		this.accion = accion;
	}

	public Catalogo getNivel() {
		return nivel;
	}

	public void setNivel(Catalogo nivel) {
		this.nivel = nivel;
	}

	public void setMedida(Catalogo medida) {
		this.medida = medida;
	}
	
	public void calculaAccronimo() {
		String nivelLocal = "";
		if((nivel != null && nivel.getAcronimo().length() > 0) || nivel.getAcronimo().equals("NOAPLICA")) {
			nivelLocal = nivel.getAcronimo().trim().replace("\t", "").replace(" ", "");
		}
		String acronimoL = canal.getAcronimo().trim().replace("\t", "").replace(" ", "")
				+ perfil.getAcronimo().trim().replace("\t", "").replace(" ", "")
				+ medida.getAcronimo().trim().replace("\t", "").replace(" ", "")
				+ nivelLocal;
		acronimo = acronimoL.toUpperCase();
	}
	
	
	

	/*public int getIndexArchivo() {
		return indexArchivo;
	}

	public void setIndexArchivo(int indexArchivo) {
		this.indexArchivo = indexArchivo;
	}*/
	
	
}
