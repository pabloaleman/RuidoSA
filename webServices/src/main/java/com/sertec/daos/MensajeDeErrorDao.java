package com.sertec.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sertec.domain.MensajesDeError;

@Repository
public class MensajeDeErrorDao {
	
	private static final Logger LOGGER = Logger.getLogger(MensajeDeErrorDao.class);
	@PersistenceContext(unitName = "ruidoPU")
    private EntityManager em;
	
	public List<MensajesDeError> getMensajes() {
		return em.createNamedQuery("Mensajes.findMensajes", MensajesDeError.class).getResultList();
	}
	
	public MensajesDeError getMensajeByAcronimo(String acronimo) {
		LOGGER.info("Buscando error por acronimo: " + acronimo);
		return em.createNamedQuery("Mensajes.findMensajesByAcronimo", MensajesDeError.class)
				.setParameter("acronimo", acronimo)
				.getResultList().get(0);
	}

}
