package com.sertec.lb;

import java.util.Date;
import java.util.List;

import com.sertec.beans.CalculaPromediosBean;
import com.sertec.domain.Estacion;
import com.sertec.domain.Parametro;
import com.sertec.exceptions.CalculaPromediosException;

public interface CalculaPromediosService {
	public CalculaPromediosBean calculaPromedios(Date fechaInicio, Date fechaFin, Estacion estacion, List<Parametro> parametrosCP) throws CalculaPromediosException;

}
