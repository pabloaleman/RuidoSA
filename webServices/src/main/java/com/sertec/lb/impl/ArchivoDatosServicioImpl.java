package com.sertec.lb.impl;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sertec.daos.ArchivoDatosDao;
import com.sertec.domain.ArchivoDatos;
import com.sertec.lb.ArchivoDatosServicio;

@Service
@Named("archivoDatosServicio")
public class ArchivoDatosServicioImpl implements ArchivoDatosServicio {
	@Autowired
	ArchivoDatosDao archivoDatosDao;

	@Override
	public void persisteArchivo(ArchivoDatos archivoDatos) {
		archivoDatosDao.persisteArchivo(archivoDatos);
	}

}
