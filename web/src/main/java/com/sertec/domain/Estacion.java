package com.sertec.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "estacion")
@NamedQueries({
    @NamedQuery(name = "Estacion.findEstacionesActivas", query = "SELECT e FROM Estacion e WHERE e.estado = 'Activa'"),
    @NamedQuery(name = "Estacion.findEstacionByAcronimo", query = "SELECT e FROM Estacion e WHERE e.acronimo = :acronimo and e.estado = 'Activa'")})
public class Estacion implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@SequenceGenerator(name="estacion_seq", sequenceName="estacion_seq",  allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="estacion_seq")
	@NotNull
	@Column(name = "id")
	private Long id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "nombre")
	private String nombre;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "acronimo")
	private String acronimo;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "estado")
	private String estado = "Activa";

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "estacion")
    private Collection<ArchivoBinario> archivosBinarios;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "estacion")
    private Collection<ArchivoDatos> archivosDatos;
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
	public String getAcronimo() {
		return acronimo;
	}
	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Collection<ArchivoBinario> getArchivosBinarios() {
		return archivosBinarios;
	}
	public void setArchivosBinarios(Collection<ArchivoBinario> archivosBinarios) {
		this.archivosBinarios = archivosBinarios;
	}
	public Collection<ArchivoDatos> getArchivosDatos() {
		return archivosDatos;
	}
	public void setArchivosDatos(Collection<ArchivoDatos> archivosDatos) {
		this.archivosDatos = archivosDatos;
	}
}
