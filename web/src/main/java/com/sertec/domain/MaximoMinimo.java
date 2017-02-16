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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "maximo_minimo")
public class MaximoMinimo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@SequenceGenerator(name="maximo_minimo_seq", sequenceName="maximo_minimo_seq",  allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="maximo_minimo_seq")
	@Column(name = "id")
	private Long id;

	@Basic(optional = false)
	@NotNull
	@Column(name = "fecha")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "valor")
	private Double valor;
	
	@JoinColumn(name = "archivoDatos", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private ArchivoDatos archivoDatos;
	
	@JoinColumn(name = "tipoPromedio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private TipoPromedio tipoPromedio;

}
