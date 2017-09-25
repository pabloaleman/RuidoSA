package com.sertec.lb;

import java.util.Date;
import java.util.List;

import org.primefaces.model.chart.LineChartModel;

import com.sertec.domain.Catalogo;
import com.sertec.domain.Dato;
import com.sertec.domain.DatoCalculado;
import com.sertec.domain.Estacion;
import com.sertec.domain.Parametro;
import com.sertec.exceptions.GeneraReporteException;

public interface ReporteServicio {
	public List<Dato> getListDatos(Date fechaInicio, Date fechaFin, List<Parametro> parametros, List<Estacion> estaciones) throws GeneraReporteException;
	
	public LineChartModel graficarFindAllByFechasAndEstacionAndContaminante(
            Date fechaInicio, Date fechaFin, List<Parametro> parametros,
            List<Estacion> estaciones)throws GeneraReporteException;
	
	public List<DatoCalculado> getListPromedios(Date fechaInicio, Date fechaFin, List<Parametro> parametros,
			List<Estacion> estaciones, List<Catalogo> tiposDato) throws GeneraReporteException;

}
