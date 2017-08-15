package com.sertec.lb.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sertec.daos.ParametroDao;
import com.sertec.domain.Parametro;
import com.sertec.exceptions.ExisteParametroException;
import com.sertec.exceptions.ParametrosException;
import com.sertec.lb.ParametroServicio;

@Service
@Named("parametroServicio")
public class ParametroServicioImpl implements ParametroServicio {
	
	@Autowired
	ParametroDao parametroDao;

	@Override
	public Map<String, Parametro> getMapaParametros() {
		List<Parametro> params = parametroDao.getParametros();
		Map<String, Parametro> retorno = new HashMap<>();
		for(Parametro param : params) {
			retorno.put(param.getAcronimo(), param);
		}
		return retorno;
	}

	@Override
	public List<Parametro> getParametros() {
		return parametroDao.getParametros();
	}

	@Override
	public void guardaParametro(Parametro parametro) throws ExisteParametroException {
		parametroDao.guardaParametro(parametro);
	}

	@Override
	public List<Parametro> getParametrosPromedio() throws ParametrosException {
		return parametroDao.getParamsCP();
	}
}
