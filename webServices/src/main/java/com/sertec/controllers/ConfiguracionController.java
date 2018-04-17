package com.sertec.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.sertec.domain.Configuracion;
import com.sertec.lb.ConfiguracionLB;

@ManagedBean
@Named("configuracionController")
@Scope("session")
public class ConfiguracionController implements Serializable {
	
	private static final long serialVersionUID = -408367331521436496L;

	@Autowired
	ConfiguracionLB configuracionLB;
	
	List<Configuracion> configuraciones;
	
	
	@PostConstruct
	public void init() {
		configuraciones = configuracionLB.getAllConfigurations();
	}
	
	public List<Configuracion> getConfiguraciones() {
		return configuraciones;
	}

	public void setConfiguraciones(List<Configuracion> configuraciones) {
		this.configuraciones = configuraciones;
	}
	
    public void onCellEdit(CellEditEvent event) {
    	int indice = event.getRowIndex();
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        System.out.println("entra a celledit"); 
        if(newValue != null && !newValue.equals(oldValue)) {
        	System.out.println(newValue);
        	Configuracion configuracionCambiada = configuraciones.get(indice);
        	configuracionLB.actualizar(configuracionCambiada);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Configuracion modificada", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
