package com.sertec.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import com.sertec.domain.Estacion;


@Repository
public class EstacionDao {
	
	@PersistenceContext(unitName = "ruidoPU")
    private EntityManager em;
	
	private static final Logger LOGGER = Logger.getLogger(EstacionDao.class);
	
	public List<Estacion> getEstacionesActivas() {
		LOGGER.debug("Buscando estaciones activas");
		return em.createNamedQuery("Estacion.findEstacionesActivas", Estacion.class).getResultList();
	}
	
	
	public Estacion findEstacionByAcronimo(String acronimo) {
		LOGGER.debug("Buscando estación por acrónimo");
		List<Estacion> estaciones = em.createNamedQuery("Estacion.findEstacionByAcronimo", Estacion.class)
				.setParameter("acronimo", acronimo)
				.getResultList();
		return estaciones.isEmpty() ? null : estaciones.get(0);
	}
	
	public Estacion getById(Long id) {
		List<Estacion> estaciones = em.createNamedQuery("Estacion.getById", Estacion.class)
				.setParameter("id", id)
				.getResultList();
		return estaciones.isEmpty() ? null : estaciones.get(0);
	}
}
