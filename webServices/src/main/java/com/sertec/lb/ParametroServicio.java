package com.sertec.lb;

import java.util.List;
import java.util.Map;

import com.sertec.domain.Parametro;
import com.sertec.exceptions.ExisteParametroException;
import com.sertec.exceptions.ParametrosException;

public interface ParametroServicio {
	public Map<String, Parametro> getMapaParametros();
	public List<Parametro> getParametros();
	public void guardaParametro(Parametro parametro)  throws ExisteParametroException;
	public List<Parametro> getParametrosPromedio() throws ParametrosException;
}
