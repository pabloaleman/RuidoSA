package com.sertec.lb.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sertec.daos.CatalogoDao;
import com.sertec.domain.Catalogo;
import com.sertec.enums.CatalogoEnum;
import com.sertec.exceptions.NoCatalogosException;
import com.sertec.lb.CatalogoServicio;

@Service
@Named("catalogoServicio")
public class CatalogoServicioImpl implements CatalogoServicio {
	
	@Autowired
	CatalogoDao catalogoDao;

	@Override
	public void guardaCatalogo(Catalogo catalogo) {
		catalogoDao.guardaCatalogo(catalogo);
	}
	
	@Override
	public void actualizaCatalogo(Catalogo catalogo) {
		catalogoDao.actualizaCatalogo(catalogo);
	}

	@Override
	public List<Catalogo> getByTipo(CatalogoEnum tipo) throws NoCatalogosException {
		return catalogoDao.getByTipo(tipo);
	}
	
	public Catalogo getByTipoAndAcronimo(String acronimo, CatalogoEnum tipo) throws NoCatalogosException {
		return catalogoDao.getByTipoAndAcronimo(acronimo, tipo);
	}

	@Override
	public Catalogo getCatalogoPromedioHora() {
		try {
			return catalogoDao.getByTipoAndAcronimo("1h", CatalogoEnum.TIPO_DATO);
		} catch (NoCatalogosException e) {
			return null;
		}
		
	}

}
