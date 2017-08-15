package com.sertec.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import com.sertec.domain.Usuario;
import com.sertec.exceptions.NoUsuarioEncontradoException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UsuarioDao extends AbstractFacade<UsuarioDao> {

	@PersistenceContext(unitName = "ruidoPU")
    private EntityManager em;
	
	private static final Logger LOGGER = Logger.getLogger(UsuarioDao.class);
	
	
	public Usuario findByUserName(String username) throws NoUsuarioEncontradoException {
		LOGGER.debug("Buscando usuarios con userName: " + username);
		List<Usuario> usuarios = em.createNamedQuery("Usuario.findByUsername", Usuario.class)
			.setParameter("userName", username)
			.getResultList();
		if(usuarios.isEmpty()) {
			throw new NoUsuarioEncontradoException("No se encontro el usuari con username: " + username);
		}
		return usuarios.get(0);
	}
	
	public UsuarioDao() {
		super(UsuarioDao.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}
}
