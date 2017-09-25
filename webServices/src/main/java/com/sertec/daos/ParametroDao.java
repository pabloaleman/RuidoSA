package com.sertec.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sertec.domain.Parametro;
import com.sertec.enums.AccionesEnum;
import com.sertec.exceptions.ExisteParametroException;
import com.sertec.exceptions.ParametrosException;

@Repository
@Transactional
public class ParametroDao {
	@PersistenceContext(unitName = "ruidoPU")
    private EntityManager em;
	
	public List<Parametro> getParametros() {
		return em.createNamedQuery("Parametro.getParametros", Parametro.class)
				.getResultList();
	}
	
	public Parametro getById(Long id) {
		List<Parametro> lista = em.createNamedQuery("Parametro.getParametroById", Parametro.class)
				.setParameter("id", id)
				.getResultList();
		return lista.isEmpty() ? null : lista.get(0);
	}
	
	public void guardaParametro(Parametro parametro)  throws ExisteParametroException {
		if(parametro.getAccion() == AccionesEnum.PERSIST) {
			List<Parametro> lista = getByCanalAndMedidaAndPerfilAndNivel(parametro);
			if(!lista.isEmpty()) {
				throw new ExisteParametroException("El parametro que intenta registrar ya existe en base de datos", parametro);
			}
		}
		
		parametro.calculaAccronimo();
		if(parametro.getId() == null) {
			em.persist(parametro);
			em.flush();
		} else {
			em.merge(parametro);
			em.flush();
		}
	}
	
	private List<Parametro> getByCanalAndMedidaAndPerfilAndNivel(Parametro parametro) {
		return em.createNamedQuery("Parametro.getByCanalAndMedidaAndPerfilAndNivel", Parametro.class)
				.setParameter("canal", parametro.getCanal())
				.setParameter("medida", parametro.getMedida())
				.setParameter("perfil", parametro.getPerfil())
				.setParameter("nivel", parametro.getNivel())
				.getResultList();
		
	}
	
	public List<Parametro> getParamsCP() throws ParametrosException {
		List<Parametro> lista = em.createNamedQuery("Parametro.getParametrosCalculaPromedios", Parametro.class)
				.getResultList();
		if(lista.isEmpty()) {
			throw new ParametrosException("No hay parámetros para calcular promedios", "NoParametrosCalculaPromedios");
		} else {
			return lista;
		}
	}
}
