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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tipo_promedio")
public class TipoPromedio implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@SequenceGenerator(name="tipo_promedio_seq", sequenceName="tipo_promedio_seq",  allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tipo_promedio_seq")
	@Column(name = "id")
	private Long id;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "tipo_promedio")
	private String tipoPromedio;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoPromedio")
    private Collection<Promedio> promedios;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoPromedio")
    private Collection<MaximoMinimo> maximosMinimos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoPromedio() {
		return tipoPromedio;
	}

	public void setTipoPromedio(String tipoPromedio) {
		this.tipoPromedio = tipoPromedio;
	}

	public Collection<Promedio> getPromedios() {
		return promedios;
	}

	public void setPromedios(Collection<Promedio> promedios) {
		this.promedios = promedios;
	}

	public Collection<MaximoMinimo> getMaximosMinimos() {
		return maximosMinimos;
	}

	public void setMaximosMinimos(Collection<MaximoMinimo> maximosMinimos) {
		this.maximosMinimos = maximosMinimos;
	}
	
	
}
