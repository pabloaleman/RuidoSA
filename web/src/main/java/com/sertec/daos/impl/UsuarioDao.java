package com.sertec.daos.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import com.sertec.domain.Usuario;

import org.apache.log4j.Logger;

public class UsuarioDao extends AbstractFacadeImpl<UsuarioDao> {

	@PersistenceContext(unitName = "ruidoPU")
    private EntityManager em;
	
	private static final Logger LOGGER = Logger.getLogger(UsuarioDao.class);
	
	
	public Usuario findByUserName(String username) {
		LOGGER.debug("Buscando usuarios con userName: " + username);
		List<Usuario> usuarios = em.createNamedQuery("Usuario.findByUsername", Usuario.class)
			.setParameter("userName", username)
			.getResultList();
		return usuarios.isEmpty() ? null : usuarios.get(0);
	}
	
	public UsuarioDao() {
		super(UsuarioDao.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}
}
