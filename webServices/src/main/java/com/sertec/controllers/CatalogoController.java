package com.sertec.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.megasoftworks.jsfUtil.MessageUtils;
import com.megasoftworks.jsfUtil.Redireccion;
import com.megasoftworks.jsfUtil.enums.SeverityMessageEnum;
import com.sertec.domain.Catalogo;
import com.sertec.enums.CatalogoEnum;
import com.sertec.exceptions.NoCatalogosException;
import com.sertec.lb.CatalogoServicio;

@ManagedBean
@Named("catalogoController")
@Scope("session")
public class CatalogoController implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CatalogoController.class);
	
	private Catalogo catalogo;
	private List<Catalogo> catalogos;
	private CatalogoEnum tipoEnum;
	
	@Autowired
	protected CatalogoServicio catalogoServicio;
	
	public void creaCatalogo() {
		catalogo = new Catalogo();
		catalogo.setTipo(tipoEnum.toString());
		Redireccion.redirecciona("/ruido/secured/admin/catalogos/edit.xhtml");
	}
	
	public void administraCatalogos(String tipo) {
		try {
			tipoEnum = tipo.equals(CatalogoEnum.CANAL.toString()) ? CatalogoEnum.CANAL : 
				tipo.equals(CatalogoEnum.MEDIDA.toString()) ? CatalogoEnum.MEDIDA :
				tipo.equals(CatalogoEnum.PERFIL.toString()) ? CatalogoEnum.PERFIL :
				tipo.equals(CatalogoEnum.NIVEL.toString()) ? CatalogoEnum.NIVEL : null;
			if(tipoEnum == null) {
				
				System.out.println("Errror tipo null");
			}
			catalogos = catalogoServicio.getByTipo(tipoEnum);
		} catch (NoCatalogosException e) {
			LOGGER.fatal(e.getMessage());
			catalogos = new ArrayList<>();
			MessageUtils.showMessage(SeverityMessageEnum.WARN, "Advertencia", e.getMessage());
		} finally {
			Redireccion.redirecciona("/ruido/secured/admin/catalogos/list.xhtml");
		}
		
	}
	
	public void editaCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
		Redireccion.redirecciona("/ruido/secured/admin/catalogos/edit.xhtml");
	}
	
	public void eliminaCatalogo() {
		Redireccion.redirecciona("/ruido/secured/admin/catalogos/list.xhtml");
	}
	
	public void guardaCatalogo() {
		try {
			if(catalogo.getId() == null) {
				catalogoServicio.guardaCatalogo(catalogo);
			} else {
				catalogoServicio.actualizaCatalogo(catalogo);
			}
			catalogos = catalogoServicio.getByTipo(tipoEnum);
		} catch (NoCatalogosException e) {
			LOGGER.fatal(e.getMessage());
			MessageUtils.showMessage(SeverityMessageEnum.WARN, "Advertencia", e.getMessage());
		} finally {
			Redireccion.redirecciona("/ruido/secured/admin/catalogos/list.xhtml");
		}
	}
	
	
	public Catalogo getCatalogo() {
		return catalogo;
	}
	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}
	public List<Catalogo> getCatalogos() {
		return catalogos;
	}
	public void setCatalogos(List<Catalogo> catalogos) {
		this.catalogos = catalogos;
	}
	public CatalogoEnum getTipoEnum() {
		return tipoEnum;
	}
	public void setTipoEnum(CatalogoEnum tipoEnum) {
		this.tipoEnum = tipoEnum;
	}
	
}
