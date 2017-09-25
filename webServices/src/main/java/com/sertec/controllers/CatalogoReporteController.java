package com.sertec.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.sertec.domain.Catalogo;
import com.sertec.enums.CatalogoEnum;
import com.sertec.enums.TipoDatoEnum;
import com.sertec.exceptions.NoCatalogosException;
import com.sertec.lb.CatalogoServicio;

@ManagedBean
@Named("catalogoReporteController")
@Scope("request")
public class CatalogoReporteController implements Serializable {

	private static final long serialVersionUID = -5908243924089675567L;
	private static final Logger LOGGER = Logger.getLogger(CatalogoReporteController.class);
	
	private List<Catalogo> tiposDato;
	
	@Autowired
	CatalogoServicio catalogoServicio;
	
	@PostConstruct
	private void init() {
		cargaTiposDato();
	}
	
	private void cargaTiposDato() {
		try {
			tiposDato = catalogoServicio.getByTipo(CatalogoEnum.TIPO_DATO);
			for(Catalogo tipoDato : tiposDato) {
				if(tipoDato.getAcronimo().equals(TipoDatoEnum.UNA_HORA.getTipoDato())) {
					tiposDato.remove(tipoDato);
					break;
				}
			}
		} catch (NoCatalogosException e) {
			LOGGER.error("No hay tipos de dato en la base de datos");
		}
	}

	public List<Catalogo> getTiposDato() {
		return tiposDato;
	}

	public void setTiposDato(List<Catalogo> tiposDato) {
		this.tiposDato = tiposDato;
	}
	
}
