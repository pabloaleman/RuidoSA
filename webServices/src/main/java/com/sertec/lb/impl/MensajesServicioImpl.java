package com.sertec.lb.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import com.sertec.daos.MensajeDeErrorDao;
import com.sertec.domain.MensajesDeError;
import com.sertec.lb.MensajesServicio;

@Service
@Named("mensajesServicio")
public class MensajesServicioImpl implements MensajesServicio {
	
	@Inject
	MensajeDeErrorDao mensajeDeErrorDao;

	@Override
	public List<MensajesDeError> getMensajes() {
		return mensajeDeErrorDao.getMensajes();
	}

	@Override
	public MensajesDeError getMensajeByAcronimo(String acronimo) {
		return mensajeDeErrorDao.getMensajeByAcronimo(acronimo);
	}

}
