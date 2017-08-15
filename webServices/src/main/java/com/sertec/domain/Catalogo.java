package com.sertec.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "catalogo")
@NamedQueries({
    @NamedQuery(name = "Catalogo.getCatalogos", query = "SELECT c FROM Catalogo c"),
    @NamedQuery(name = "Catalogo.getCatalogoById", query = "SELECT c FROM Catalogo c where c.id = :id"),
    @NamedQuery(name = "Catalogo.getCatalogosByTipo", query = "SELECT c FROM Catalogo c where c.tipo = :tipo"),
    @NamedQuery(name = "Catalogo.getCatalogosByTipoAndAcronimo", query = "SELECT c FROM Catalogo c where c.tipo = :tipo and c.acronimo = :acronimo")})
public class Catalogo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@SequenceGenerator(name="catalogo_seq", sequenceName="catalogo_seq",  allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="catalogo_seq")
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
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "tipo")
	private String tipo;
	
	public Catalogo() {
		
	}
	
	public Catalogo(String descripcion) {
		this.descripcion = descripcion;
	}

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Catalogo)) {
            return false;
        }
        Catalogo other = (Catalogo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if(this.id == other.id) {
        	return true;
        }
        return true;
    }
	
}