package com.sertec.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

@ViewScoped
@ManagedBean
@Named("menuController")
public class MenuController {
	public String subirArchivo() {
		return "pages/subirArchivo";
	}
	
	public String inicio() {
		return "/index";
	}

}
