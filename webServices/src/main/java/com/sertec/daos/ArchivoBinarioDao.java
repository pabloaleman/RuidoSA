package com.sertec.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sertec.domain.ArchivoBinario;
import com.sertec.domain.Estacion;

@Repository
public class ArchivoBinarioDao {
	@PersistenceContext(unitName = "ruidoPU")
    private EntityManager em;
	
	private static final Logger LOGGER = Logger.getLogger(ArchivoBinarioDao.class);
	
	public boolean hayArchivo(String nombreArchivo, Estacion estacion) {
		LOGGER.debug("Buscando archivo: " + nombreArchivo);
		List<ArchivoBinario> archivosBinarios =  em.createNamedQuery("ArchivoBinario.findByNombreAndEstacion", ArchivoBinario.class)
				.setParameter("nombre", nombreArchivo)
				.setParameter("estacion", estacion)
				.getResultList();
		return archivosBinarios.isEmpty() ? false : true;
	}
	@Transactional
	public boolean save(ArchivoBinario ab) {
		LOGGER.debug("Guardando archivo en bdd: " + ab.getNombre());
		LOGGER.debug("Estacion asignada: " + ab.getEstacion().getNombre());
		try{
			//em.getTransaction().begin();
			em.persist(ab);
			em.flush();
			//em.getTransaction().commit();
			return true;
		} catch(Exception e) {
			LOGGER.error("Error al guardar el archivo en bdd: " + e.getMessage());
			return false;
		}
	}
	
	public List<ArchivoBinario> getArchivos(Estacion estacion) {
		return em.createNamedQuery("ArchivoBinario.findByEstacion", ArchivoBinario.class)
				.setParameter("estacion", estacion)
				.getResultList();
		
	}

}
