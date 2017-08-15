package com.sertec.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.megasoftworks.jsfUtil.MessageUtils;
import com.megasoftworks.jsfUtil.Redireccion;
import com.megasoftworks.jsfUtil.enums.SeverityMessageEnum;
import com.sertec.domain.Catalogo;
import com.sertec.domain.Parametro;
import com.sertec.enums.CatalogoEnum;
import com.sertec.exceptions.ExisteParametroException;
import com.sertec.exceptions.NoCatalogosException;
import com.sertec.lb.CatalogoServicio;
import com.sertec.lb.ParametroServicio;

@ManagedBean
@Named("parametroController")
@Scope("session")
public class ParametroController  implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ParametroController.class);
	
	private List<Catalogo> canales;
	private List<Catalogo> medidas;
	private List<Catalogo> perfiles;
	private List<Catalogo> niveles;
	private List<Parametro> parametros;
	private Parametro parametro;
	private SelectItem nivelNA;
	
	@Autowired
	private CatalogoController catalogoController;
	
	@Autowired
	CatalogoServicio catalogoServicio;
	
	@Autowired
	ParametroServicio parametroServicio;
	
	@PostConstruct
	private void init() {
		Catalogo nivelNAC = new Catalogo();
		nivelNAC.setAcronimo("NOAPLICA");
		nivelNAC.setDescripcion("Nivel de n aplica");
		nivelNAC.setId(-1L);
		nivelNAC.setTipo(CatalogoEnum.NIVEL.getNombre());
		nivelNA = new SelectItem();
		nivelNA.setValue(nivelNAC);
		nivelNA.setLabel("No aplica");
		nivelNA.setDescription("Nivel que no aplica");
	}
	
	public void adminParametros() {
		parametros = parametroServicio.getParametros();
		Redireccion.redirecciona("/ruido/secured/admin/parametros/list.xhtml");
	}
	
	public void creaParametro() {
			cargaListasInit();
			parametro = new Parametro();
			Redireccion.redirecciona("/ruido/secured/admin/parametros/edit.xhtml");
	}
	
	public void guardaParametro() {
		try {
			parametroServicio.guardaParametro(parametro);
			parametros = parametroServicio.getParametros();
			Redireccion.redirecciona("/ruido/secured/admin/parametros/list.xhtml");
		} catch (ExisteParametroException e) {
			MessageUtils.showMessage(SeverityMessageEnum.WARN, "Advertencia", e.getMessage());
			Redireccion.redirecciona("/ruido/secured/admin/parametros/edit.xhtml");
		}
		
	}
	
	public void editaParametro(Parametro parametro) {
		cargaListasInit();
		this.parametro = parametro;
		Redireccion.redirecciona("/ruido/secured/admin/parametros/edit.xhtml");
	}
	
	private void cargaListasInit() {
		try {
			canales = catalogoServicio.getByTipo(CatalogoEnum.CANAL);
			medidas = catalogoServicio.getByTipo(CatalogoEnum.MEDIDA);
			perfiles = catalogoServicio.getByTipo(CatalogoEnum.PERFIL);
			niveles = catalogoServicio.getByTipo(CatalogoEnum.NIVEL);
		} catch (NoCatalogosException e) {
			LOGGER.warn("Se redirecciona a la pagina para que cree los catalogos");
			MessageUtils.showMessage(SeverityMessageEnum.ERROR, "Error", e.getMessage());
			catalogoController.administraCatalogos(e.getCatalogo().toString());
		}
	}

	public List<Catalogo> getCanales() {
		return canales;
	}

	public void setCanales(List<Catalogo> canales) {
		this.canales = canales;
	}

	public List<Catalogo> getMedidas() {
		return medidas;
	}

	public void setMedidas(List<Catalogo> medidas) {
		this.medidas = medidas;
	}

	public List<Catalogo> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Catalogo> perfiles) {
		this.perfiles = perfiles;
	}
	
	

	public List<Catalogo> getNiveles() {
		return niveles;
	}

	public void setNiveles(List<Catalogo> niveles) {
		this.niveles = niveles;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public CatalogoController getCatalogoController() {
		return catalogoController;
	}

	public void setCatalogoController(CatalogoController catalogoController) {
		this.catalogoController = catalogoController;
	}

	public List<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	public SelectItem getNivelNA() {
		return nivelNA;
	}

	public void setNivelNA(SelectItem nivelNA) {
		this.nivelNA = nivelNA;
	}
	
	
	
	
	
}
