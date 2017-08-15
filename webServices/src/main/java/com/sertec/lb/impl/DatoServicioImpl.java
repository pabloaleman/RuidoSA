package com.sertec.lb.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sertec.daos.DatoDao;
import com.sertec.domain.ArchivoDatos;
import com.sertec.domain.Catalogo;
import com.sertec.domain.Dato;
import com.sertec.lb.DatoServicio;

@Service
@Named("datoServicio")
public class DatoServicioImpl implements DatoServicio {

	@Autowired
	DatoDao datoDao;
	@Override
	public void persisteDatos(List<Dato> datos, ArchivoDatos archivoDatos, Catalogo tipoDato) {
		datoDao.persisteDatos(datos, archivoDatos, tipoDato);
	}

}
