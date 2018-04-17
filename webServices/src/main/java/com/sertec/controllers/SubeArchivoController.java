package com.sertec.controllers;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.megasoftworks.gl.poi.XlsxManager;
import com.megasoftworks.jsfUtil.MessageUtils;
import com.megasoftworks.jsfUtil.enums.SeverityMessageEnum;
import com.sertec.beans.PreprocesoArchivoBean;
import com.sertec.domain.Catalogo;
import com.sertec.domain.Configuracion;
import com.sertec.domain.Parametro;
import com.sertec.domain.Usuario;
import com.sertec.enums.CatalogoEnum;
import com.sertec.enums.TipoDatoEnum;
import com.sertec.exceptions.CargaArchivoException;
import com.sertec.exceptions.NoCatalogosException;
import com.sertec.lb.CatalogoServicio;
import com.sertec.lb.ConfiguracionLB;
import com.sertec.lb.ProcesaArchivoServicio;
import com.sertec.lb.UsuarioServicio;

/**
 *
 * @author pablo
 */
@ManagedBean
@Named("subeArchivoController")
@Scope("session")
public class SubeArchivoController implements Serializable {
	
	private static final Logger LOGGER = Logger.getLogger(SubeArchivoController.class);
	
	private Configuracion formatoFechaConfig;
    
	private static final long serialVersionUID = 8553359155788230925L;
	
	private UploadedFile file;
	private PreprocesoArchivoBean preprocesoArchivoBean;
	//private String cadenaMensajePreproceso = "";
	private boolean presentaSubeArchivo = true;
	private boolean presentaTablaDatos = false;
	private List<Parametro> parametrosACargar;
	private List<Parametro> parametrosNoBdd;
	
	
	@Autowired
	ConfiguracionLB configuracionLB;
	
	@Autowired
	ProcesaArchivoServicio procesaArchivoServicio;
	
	@Autowired
	CatalogoServicio catalogoServicio;
	
	@Autowired
	EstacionController estacionController;
	@Autowired
	LoginController loginController;
	@Autowired
	UsuarioServicio usuarioServicio;
	
	
	@PostConstruct
	public void init() {
		formatoFechaConfig = configuracionLB.getConfiguracionPorItem("FORMATO_FECHA");
	}
	
	public void reiniciaForm() {
		file = null;
		presentaSubeArchivo = true;
		presentaTablaDatos = false;
		procesaArchivoServicio.reiniciaBean();
		preprocesoArchivoBean = new PreprocesoArchivoBean();
		parametrosACargar = new ArrayList<>();
		parametrosNoBdd = new ArrayList<>();
	}

	/**
	 * Funcion que sube el archivo y lo procesa
	 * @param event el evento de subida del archivo propio de primefaces
	 */
	public void handleFileUpload(FileUploadEvent event) {
		file = event.getFile();
		
		try {
			if(file != null) {
	            String contenido = XlsxManager.readXlsxFile((FileInputStream)file.getInputstream(), ";");
	            if(procesaArchivoServicio == null) {
	            	System.out.println("es nulo");
	            }
	            preprocesoArchivoBean = procesaArchivoServicio.procesaArchivo(contenido, formatoFechaConfig.getValor());
	            
	            if(!preprocesoArchivoBean.getParametroscargar().isEmpty()) {
	            	parametrosACargar = preprocesoArchivoBean.getParametroscargar();
	            	//cadenaMensajePreproceso = "Parametros encontrados: " + StringUtils.join(preprocesoArchivoBean.getParametroscargar(), ", "); 
	            } else {
	            	parametrosACargar = new ArrayList<>();
	            }
	            if(!preprocesoArchivoBean.getParametrosNoBdd().isEmpty()) {
	            	parametrosNoBdd = preprocesoArchivoBean.getParametrosNoBdd();
	            	//cadenaMensajePreproceso = cadenaMensajePreproceso + "<br/> Parametros no encontrados en la base de datos" + StringUtils.join(preprocesoArchivoBean.getParametrosNoBdd(), ", ");
	            } else {
	            	parametrosNoBdd = new ArrayList<>();
	            }
	            
	            presentaSubeArchivo = false;
	            presentaTablaDatos = true;
	        } else {
	        	LOGGER.error("Problema con le archivo no se ha cargado");
			}
		} catch (CargaArchivoException e) {
			LOGGER.error(e.getMessage());
			MessageUtils.showMessage(SeverityMessageEnum.ERROR, "Error al subir el archivo", e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			MessageUtils.showMessage(SeverityMessageEnum.ERROR, "Error al subir el archivo", "Revisar el log del sistema");
			e.printStackTrace();
		}
    }
	
	/**
	 * Funcion que procesa los datos y los sube a la base de datos y reinicia el formulario.
	 */
	public void procesaDatos() {
		try {
			Usuario usuario = loginController.getUsuario();
			Catalogo tipoDato = catalogoServicio.getByTipoAndAcronimo(TipoDatoEnum.UNA_HORA.toString(), CatalogoEnum.TIPO_DATO);
			
			procesaArchivoServicio.persisteDatos(preprocesoArchivoBean, file.getFileName(),
					estacionController.getEstacionSeleccionada(), usuario, tipoDato);
			reiniciaForm();
		} catch (NoCatalogosException e) {
			LOGGER.error(e.getMessage());
		} 
		
	}
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public PreprocesoArchivoBean getPreprocesoArchivoBean() {
		return preprocesoArchivoBean;
	}

	public void setPreprocesoArchivoBean(PreprocesoArchivoBean preprocesoArchivoBean) {
		this.preprocesoArchivoBean = preprocesoArchivoBean;
	}

	public boolean isPresentaSubeArchivo() {
		return presentaSubeArchivo;
	}

	public void setPresentaSubeArchivo(boolean presentaSubeArchivo) {
		this.presentaSubeArchivo = presentaSubeArchivo;
	}

	public boolean isPresentaTablaDatos() {
		return presentaTablaDatos;
	}

	public void setPresentaTablaDatos(boolean presentaTablaDatos) {
		this.presentaTablaDatos = presentaTablaDatos;
	}

	public List<Parametro> getParametrosACargar() {
		return parametrosACargar;
	}

	public void setParametrosACargar(List<Parametro> parametrosACargar) {
		this.parametrosACargar = parametrosACargar;
	}

	public List<Parametro> getParametrosNoBdd() {
		return parametrosNoBdd;
	}

	public void setParametrosNoBdd(List<Parametro> parametrosNoBdd) {
		this.parametrosNoBdd = parametrosNoBdd;
	}	
}
