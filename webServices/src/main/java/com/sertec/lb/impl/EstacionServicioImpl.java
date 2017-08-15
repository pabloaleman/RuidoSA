package com.sertec.lb.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sertec.daos.EstacionDao;
import com.sertec.domain.Estacion;
import com.sertec.lb.EstacionServicio;

@Service
@Named("estacionServicio")
public class EstacionServicioImpl implements EstacionServicio {

	@Autowired
	EstacionDao estacionDao;
	
	@Override
	public List<Estacion> getEstacionesActivas() {
		return estacionDao.getEstacionesActivas();
	}

}
