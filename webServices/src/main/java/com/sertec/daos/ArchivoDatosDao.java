package com.sertec.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sertec.domain.ArchivoDatos;

@Repository
@Transactional
public class ArchivoDatosDao {
	@PersistenceContext(unitName = "ruidoPU")
    private EntityManager em;
	
	private static final Logger LOGGER = Logger.getLogger(ArchivoDatosDao.class);
	
	public void persisteArchivo(ArchivoDatos archivoDatos) {
		try {
			em.persist(archivoDatos);
			em.flush();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

}
