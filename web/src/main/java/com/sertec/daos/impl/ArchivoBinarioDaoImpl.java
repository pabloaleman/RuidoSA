package com.sertec.daos.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sertec.daos.ArchivoBinarioDao;
import com.sertec.domain.ArchivoBinario;
import com.sertec.domain.Estacion;

@Repository
public class ArchivoBinarioDaoImpl extends AbstractFacadeImpl<ArchivoBinario> implements ArchivoBinarioDao {
	
	private static final Logger LOGGER = Logger.getLogger(ArchivoBinarioDaoImpl.class);
	
	public ArchivoBinarioDaoImpl() {
		super(ArchivoBinario.class);
	}
	
	@Override
	public boolean hayArchivo(String nombreArchivo, Estacion estacion) {
		LOGGER.debug("Buscando archivo: " + nombreArchivo);
		List<ArchivoBinario> archivosBinarios =  em.createNamedQuery("ArchivoBinario.findByNombreAndEstacion", ArchivoBinario.class)
				.setParameter("nombre", nombreArchivo)
				.setParameter("estacion", estacion)
				.getResultList();
		return archivosBinarios.isEmpty() ? false : true;
	}
	
	@Transactional
	@Override
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
	
	 @Override
	    protected EntityManager getEntityManager() {
	        return em;
	    }


}
