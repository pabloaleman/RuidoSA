package com.sertec.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sertec.domain.Configuracion;

@Repository
@Transactional
public class ConfiguracionDao {
	@PersistenceContext(unitName = "ruidoPU")
    private EntityManager em;
	
	private static final Logger LOGGER = Logger.getLogger(ConfiguracionDao.class);
	
	public Configuracion getConfiguracionPorItem(String item) {
		List<Configuracion> lista = em.createNamedQuery("Configuracion.getByItem", Configuracion.class)
				.setParameter("item", item)
				.getResultList();
		return (null != lista && !lista.isEmpty()) ? lista.get(0) : null;
	}
	
	public List<Configuracion> getAllConfigurations() {
//		LOGGER.info("Entro aca al dao");
		List<Configuracion> lista = em.createNamedQuery("Configuracion.getAllConfigurations", Configuracion.class)
				.getResultList();
		if(null == lista || lista.isEmpty()) {
			LOGGER.fatal("No hay configuraciones en el sistema");
		}
//		LOGGER.info("Tamaño de lista: " + lista.size());
		return lista;
	}
	
	public void actualiza(Configuracion configuracion) {
		em.merge(configuracion);
		em.flush();
		
	}
}
