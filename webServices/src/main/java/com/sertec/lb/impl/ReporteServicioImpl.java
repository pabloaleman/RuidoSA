package com.sertec.lb.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megasoftworks.gl.manage.Fechas;
import com.sertec.daos.DatoCalculadoDao;
import com.sertec.daos.DatoDao;
import com.sertec.domain.Catalogo;
import com.sertec.domain.Dato;
import com.sertec.domain.DatoCalculado;
import com.sertec.domain.Estacion;
import com.sertec.domain.Parametro;
import com.sertec.enums.GeneraReportesErrorEnum;
import com.sertec.exceptions.GeneraReporteException;
import com.sertec.lb.ReporteServicio;

@Service
@Named("reporteServicio")
public class ReporteServicioImpl implements ReporteServicio {

	@Autowired
	DatoDao datoDao;
	@Autowired
	DatoCalculadoDao datoCalculadoDao;
	
	@Override
	public List<Dato> getListDatos(Date fechaInicio, Date fechaFin, List<Parametro> parametros,
			List<Estacion> estaciones) throws GeneraReporteException {
		
		if(fechaFin.getTime() < fechaInicio.getTime()) {
			throw new GeneraReporteException("Fecha de inicio mayor que la fecha de fin", GeneraReportesErrorEnum.FECHA_INICIO_MAYOR_FECHA_FIN);
		} else {
			List<Dato> datosRetorno = datoDao.getDatosByEstacionAndParametroAndFechas(estaciones, parametros, fechaInicio, fechaFin);
//			for(Estacion estacion : estaciones) {
//				for(Parametro parametro : parametros) {
//					datosRetorno.addAll();
//				}
//			}
			return datosRetorno;
		}		
	}
	
	@Override
	public List<DatoCalculado> getListPromedios(Date fechaInicio, Date fechaFin, List<Parametro> parametros,
			List<Estacion> estaciones, List<Catalogo> tiposDato) throws GeneraReporteException {
		
		if(fechaFin.getTime() < fechaInicio.getTime()) {
			throw new GeneraReporteException("Fecha de inicio mayor que la fecha de fin", GeneraReportesErrorEnum.FECHA_INICIO_MAYOR_FECHA_FIN);
		} else {
			List<DatoCalculado> datosRetorno = datoCalculadoDao.getDatosByEstacionAndParametroAndFechas(tiposDato, estaciones, parametros, fechaInicio, fechaFin);	
			return datosRetorno;
		}		
	}
	
	@Override
    public LineChartModel graficarFindAllByFechasAndEstacionAndContaminante(
            Date fechaInicio, Date fechaFin, List<Parametro> parametros,
            List<Estacion> estaciones)throws GeneraReporteException {
        
		LineChartModel modelo = new LineChartModel();

        for (Estacion estacion : estaciones) {
            for (Parametro parametro : parametros) {
                List<Dato> valoresGrafico = datoDao.getDatosByEstacionAndParametroAndFechas(estacion, parametro, fechaInicio, fechaFin);
                ChartSeries valores = new ChartSeries();
                String cont = parametro.getDescripcion() + estacion.getNombre();
                valores.setLabel(cont);
                
                if(!valoresGrafico.isEmpty()) {
                    for (Dato v : valoresGrafico) {
                        valores.set(Fechas.dateToString(v.getFecha(), Fechas.formatoPrime), v.getValor());
                    }
                    modelo.addSeries(valores);
                }
            }
        }

        modelo.setTitle("Grafico de valores");
        modelo.setZoom(true);
        modelo.setLegendPosition("e");
        modelo.setAnimate(true);
        modelo.setShowPointLabels(false);
        modelo.getAxis(AxisType.Y).setLabel("Valores");

        DateAxis axis = new DateAxis("Fechas");
        axis.setTickAngle(-45);
        axis.setMax(Fechas.dateToString(fechaFin, Fechas.formatoPrime));
        axis.setMin(Fechas.dateToString(fechaInicio, Fechas.formatoPrime));
        axis.setTickFormat(Fechas.formatoOutPrimeHoras);
        modelo.getAxes().put(AxisType.X, axis);

        return modelo;
    }

}
