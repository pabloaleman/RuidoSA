package com.sertec.daos;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sertec.domain.ArchivoDatos;
import com.sertec.domain.Catalogo;
import com.sertec.domain.Dato;
import com.sertec.domain.Estacion;
import com.sertec.domain.Parametro;

@Repository
@Transactional
public class DatoDao {
	@PersistenceContext(unitName = "ruidoPU")
    private EntityManager em;
	
	private static final Logger LOGGER = Logger.getLogger(DatoDao.class);
	
	public void persisteDatos(List<Dato> datos, ArchivoDatos archivoDatos, Catalogo tipoDato) {
		for(Dato dato : datos) {
			dato.setArchivoDatos(archivoDatos);
			dato.setUsuario(archivoDatos.getUsuario());
			dato.setEstacion(archivoDatos.getEstacion());
			dato.setTipoDato(tipoDato);
			try {
				List<Dato> datosList = em.createNamedQuery("Dato.findDato", Dato.class)
						.setParameter("parametro", dato.getParametro())
						.setParameter("tipoDato", dato.getTipoDato())
						.setParameter("fechaD", dato.getFechaD())
						.getResultList();
				if(datosList.isEmpty()) {
					em.persist(dato);
					em.flush();
				} else {
					LOGGER.warn("Tratando de registrar dato previamente guardado");
				}
			} catch (Exception e) {
				LOGGER.error("Error guardando el dato");
				LOGGER.error(e.getMessage());
			}
			
		}
	}
	
	public List<Dato> getDatosParapromedio(Estacion estacion, Parametro parametro, Catalogo tipoDato, List<Integer>horas, Date fechaInicio, Date fechaFin) {
		return em.createNamedQuery("Dato.findDatosParaPromedio", Dato.class)
				.setParameter("estacion", estacion)
				.setParameter("parametro", parametro)
				.setParameter("tipoDato", tipoDato)
				.setParameter("horas", horas)
				.setParameter("fInicio", fechaInicio)
				.setParameter("fFin", fechaFin)
				.getResultList();
	}
	
	public List<Dato> getDatosByEstacionAndParametroAndFechas(List<Estacion> estaciones, List<Parametro> parametros, Date fechaInicio, Date fechaFin) {
		return em.createNamedQuery("Dato.getDatosByEstacionesAndParametrosAndFechas", Dato.class)
				.setParameter("estaciones", estaciones)
				.setParameter("parametros", parametros)
				.setParameter("fInicio", fechaInicio)
				.setParameter("fFin", fechaFin)
				.getResultList();
	}
	
	public List<Dato> getDatosByEstacionAndParametroAndFechas(Estacion estacion, Parametro parametro, Date fechaInicio, Date fechaFin) {
		return em.createNamedQuery("Dato.getDatosByEstacionAndParametroAndFechas", Dato.class)
				.setParameter("estacion", estacion)
				.setParameter("parametro", parametro)
				.setParameter("fInicio", fechaInicio)
				.setParameter("fFin", fechaFin)
				.getResultList();
	}

}
