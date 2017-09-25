package com.sertec.lb;

import java.util.Date;
import java.util.List;

import com.sertec.beans.CalculaPromediosMensajesListBean;
import com.sertec.domain.Estacion;
import com.sertec.domain.Parametro;
import com.sertec.domain.Usuario;
import com.sertec.exceptions.CalculaPromediosException;

public interface CalculaPromediosService {
	public CalculaPromediosMensajesListBean calculaPromedios(Date fechaInicio, Date fechaFin, Estacion estacion, List<Parametro> parametrosCP, Usuario usuario)  throws CalculaPromediosException;

}
