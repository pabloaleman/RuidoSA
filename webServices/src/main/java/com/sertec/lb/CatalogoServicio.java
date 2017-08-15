package com.sertec.lb;

import java.util.List;

import com.sertec.domain.Catalogo;
import com.sertec.enums.CatalogoEnum;
import com.sertec.exceptions.NoCatalogosException;

public interface CatalogoServicio {
	public void guardaCatalogo(Catalogo catalogo);
	public void actualizaCatalogo(Catalogo catalogo);
	public List<Catalogo> getByTipo(CatalogoEnum tipo) throws NoCatalogosException;
	public Catalogo getByTipoAndAcronimo(String acronimo, CatalogoEnum tipo) throws NoCatalogosException;
}
