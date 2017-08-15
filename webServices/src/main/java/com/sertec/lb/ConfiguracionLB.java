package com.sertec.lb;

import java.util.List;

import com.sertec.domain.Configuracion;

public interface ConfiguracionLB {
	public Configuracion getConfiguracionPorItem(String item);
	public List<Configuracion> getAllConfigurations();
	public void actualizar(Configuracion configuracion);

}
