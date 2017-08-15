package com.sertec.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.sertec.domain.Usuario;
import com.sertec.exceptions.NoUsuarioEncontradoException;
import com.sertec.lb.UsuarioServicio;

@ManagedBean
@Named("loginController")
@Scope("session")
public class LoginController  implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(LoginController.class);
	private Usuario usuario;
	
	@Autowired
	UsuarioServicio usuarioServicio;
	
	public String doLogin()  throws ServletException, IOException {
//		LOGGER.info("Ingresando al sistema 1");
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/login");

        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
        
        
        Map<String, String> parameterMap = (Map<String, String>) context.getRequestParameterMap();
        String param = parameterMap.get("username");
//        LOGGER.info("nombre de usuario: " + param);
        
        try {
			usuario = usuarioServicio.findByUserName(param);
		} catch (NoUsuarioEncontradoException e) {
			LOGGER.error("El usuario no ha sido encontrado en la base de datos");
			usuario = null;
		}        		
        return "";

    }
	
	public void doLogout()  throws ServletException, IOException {
		LOGGER.info("Ingresando a salir del sisteama");
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/logout");

        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
        usuario = null;
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
