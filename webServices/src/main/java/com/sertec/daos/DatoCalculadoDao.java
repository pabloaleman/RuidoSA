package com.sertec.daos;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sertec.domain.Catalogo;
import com.sertec.domain.DatoCalculado;
import com.sertec.domain.Estacion;
import com.sertec.domain.Parametro;
import com.sertec.enums.CalculaPromedioErrorEnum;
import com.sertec.exceptions.CalculaPromediosException;

@Repository
@Transactional
public class DatoCalculadoDao {
	@PersistenceContext(unitName = "ruidoPU")
    private EntityManager em;
	
	private static final Logger LOGGER = Logger.getLogger(DatoCalculadoDao.class);
	
	public void persistePromedio(DatoCalculado datoCalculado) throws CalculaPromediosException {
		try {
			em.persist(datoCalculado);
			em.flush();			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new CalculaPromediosException("Problema guardando el promedio en bdd: " + e.getMessage(), CalculaPromedioErrorEnum.PERSISTE_BDD_ERROR);
		}
		
	}
	
	public List<DatoCalculado> getDatoByEstacionDAAndTipoDatoAndFechas(Estacion estacion, Catalogo tipoDato, Date fechaI, Date fechaF) {
		return em.createNamedQuery("DatoCalculado.getDatoByEstacionDAndTipoDatoAndFechas", DatoCalculado.class)
				.setParameter("estacion", estacion)
				.setParameter("tipoDato", tipoDato)
				.setParameter("fechaI", fechaI.getTime())
				.setParameter("fechaF", fechaF.getTime())
				.getResultList();
	}
	
	public void actualizaPromediosPrevios(Estacion estacion, Catalogo tipoDato, Date fechaI, Date fechaF) {
		List<DatoCalculado> datosActualizar = getDatoByEstacionDAAndTipoDatoAndFechas(estacion, tipoDato, fechaI, fechaF);
		for(DatoCalculado dato : datosActualizar) {
			dato.setEstado("Inactivo");
			em.merge(dato);
		}
		em.flush();
	}
	
	public List<DatoCalculado> getDatosByEstacionAndParametroAndFechas(List<Catalogo> tiposDato, List<Estacion> estaciones, List<Parametro> parametros, Date fechaInicio, Date fechaFin) {
		return em.createNamedQuery("DatoCalculado.getDatosByEstacionesAndParametrosAndTiposDatoAndFechas", DatoCalculado.class)
				.setParameter("tiposDato", tiposDato)
				.setParameter("estaciones", estaciones)
				.setParameter("parametros", parametros)
				.setParameter("fInicio", fechaInicio)
				.setParameter("fFin", fechaFin)
				.getResultList();
	}
	
	
}
