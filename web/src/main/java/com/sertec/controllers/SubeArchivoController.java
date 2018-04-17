package com.sertec.controllers;

import java.io.FileInputStream;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;
import org.springframework.web.context.annotation.SessionScope;

import com.megasoftworks.gl.poi.XlsxManager;
import com.megasoftworks.jsfUtil.MessageUtils;
import com.megasoftworks.jsfUtil.enums.SeverityMessageEnum;
import com.sertec.domain.DatoHorario;

/**
 *
 * @author pablo
 */
@SessionScope
@ManagedBean
@Named("suberArchivoController")
public class SubeArchivoController {
    
	private UploadedFile file;
	private List <DatoHorario> datos;
	
	public String upload() {
		try {
			if(file != null) {
				MessageUtils.showMessage("Archivo subido", "Archivo subido" + file.getFileName());
				MessageUtils.showMessage(null, "Archivo subido");
				MessageUtils.showMessage("Archivo subido", null);
				if(!file.getFileName().endsWith("xlsx")) {
					MessageUtils.showMessage(SeverityMessageEnum.ERROR, "Error", "Debe subir archivos con extensión xlsx");
				} else {
					readXlsxFile((FileInputStream) file.getInputstream());
				}

	        } else {
	        	System.out.println("Problema con le archivo");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        
        return "";
    }
	
	private void readXlsxFile(FileInputStream fis) {
		String content = XlsxManager.readXlsxFile(fis, ";");
		String[] lineas = content.split("\n");
		//compruebo que haya datos (empiezan en la 5ta linea)
		if(lineas.length > 5 && lineas[5].startsWith("1")) {
			
		} else {
			MessageUtils.showMessage(SeverityMessageEnum.ERROR, "Error", "El archivo que ha subido no tiene datos o no es válido");
		}
		System.out.println(content);
		
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<DatoHorario> getDatos() {
		return datos;
	}

	public void setDatos(List<DatoHorario> datos) {
		this.datos = datos;
	}
	
	
}
