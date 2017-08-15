package com.sertec.lb.impl;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sertec.daos.UsuarioDao;
import com.sertec.domain.Usuario;
import com.sertec.exceptions.NoUsuarioEncontradoException;
import com.sertec.lb.UsuarioServicio;

@Service
@Named("usuarioServicio")
public class UsuarioServicioImpl implements UsuarioServicio {
	
	@Autowired
	UsuarioDao usuarioDao;
	
	@Override
	public Usuario findByUserName(String username) throws NoUsuarioEncontradoException {
		return usuarioDao.findByUserName(username);
	}

}
