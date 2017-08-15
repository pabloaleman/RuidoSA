package com.sertec.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sertec.domain.Catalogo;
import com.sertec.enums.CatalogoEnum;
import com.sertec.exceptions.NoCatalogosException;

@Repository
@Transactional
public class CatalogoDao {
	@PersistenceContext(unitName = "ruidoPU")
    private EntityManager em;
	
	private static final Logger LOGGER = Logger.getLogger(CatalogoDao.class);
	
	public void guardaCatalogo(Catalogo catalogo) {
		em.persist(catalogo);
		em.flush();
	}
	
	public void actualizaCatalogo(Catalogo catalogo) {
		em.merge(catalogo);
		em.flush();
	}
	public List<Catalogo> getByTipo(CatalogoEnum tipo) throws NoCatalogosException {
		List<Catalogo> retorno = em.createNamedQuery("Catalogo.getCatalogosByTipo", Catalogo.class)
				.setParameter("tipo", tipo.toString())
				.getResultList();
		if(retorno.isEmpty()) {
			String mensaje = "No hay catalogos del tipo: " + tipo.toString() + " por favor cargue catalogos en la base de datos";
			LOGGER.fatal(mensaje);
			throw new NoCatalogosException(mensaje, tipo);
		}
		return retorno;
	}
	
	public Catalogo getById(Long id) {
		return em.createNamedQuery("Catalogo.getCatalogoById", Catalogo.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	public Catalogo getByTipoAndAcronimo(String acronimo, CatalogoEnum tipo) throws NoCatalogosException {
		List<Catalogo> lista = em.createNamedQuery("Catalogo.getCatalogosByTipoAndAcronimo", Catalogo.class)
				.setParameter("tipo", tipo.toString())
				.setParameter("acronimo", acronimo)
				.getResultList();
		if(lista.isEmpty()) {
			String mensaje = "No hay catalogo del tipo: " + tipo.toString() + " y/o acronimo " + acronimo + " por favor cargue catalogos en la base de datos";
			LOGGER.fatal(mensaje);
			throw new NoCatalogosException(mensaje, tipo);
		}
		return lista.get(0);
	}

}
