package com.sertec.lb.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megasoftworks.gl.list.ListUtils;
import com.megasoftworks.gl.manage.Fechas;
import com.sertec.beans.CalculaPromediosBean;
import com.sertec.daos.ConfiguracionDao;
import com.sertec.daos.DatoDao;
import com.sertec.domain.Catalogo;
import com.sertec.domain.Estacion;
import com.sertec.domain.Parametro;
import com.sertec.enums.CatalogoEnum;
import com.sertec.enums.TipoDatoEnum;
import com.sertec.exceptions.CalculaPromediosException;
import com.sertec.lb.CalculaPromediosService;
import com.sertec.lb.CatalogoServicio;

@Service
@Named("calculaPromediosService")
public class CalculaPromediosServiceImpl implements CalculaPromediosService {
	
	@Autowired
	DatoDao datoDao;
	@Autowired
	CatalogoServicio catalogoServicio;
	@Autowired
	ConfiguracionDao configuracionDao;


	@Override
	public CalculaPromediosBean calculaPromedios(Date fechaInicio, Date fechaFin, Estacion estacion, List<Parametro> parametrosCP) throws CalculaPromediosException {
		CalculaPromediosBean retorno = new CalculaPromediosBean();
		if(fechaFin.getTime() < fechaInicio.getTime()) {
			throw new CalculaPromediosException("Fecha de inicio mayor que la fecha de fin", "CalculaPromedios0001");
		} else {
			Long fechaFinLong = fechaFin.getTime();
			Date fechaAux = fechaInicio;
			
			while(fechaAux.getTime() <= fechaFinLong) {
				for(Parametro parametro : parametrosCP) {
					calculaPromediosPorDia(fechaAux, parametro);
				}
				
				fechaAux = Fechas.aumentoDias(fechaAux, 1);
			}
			retorno.getMensajesInformacion().add("Todo ok");
			retorno.getMensajesError().add("Error");
			retorno.getMensajesWarning().add("Warning");
		}
		return retorno;		
	}
	private void calculaPromediosPorDia(Date fecha, Parametro parametro) throws CalculaPromediosException {
		Date fechaAuxFin = Fechas.stringToDate(Fechas.dateToString(fecha, "yyyy/MM/dd") + " 23:59:59", "yyyy/MM/dd HH:mm:ss");
		String fechaString = Fechas.dateToString(fecha, "dd/MM/yyyy");
		try {
			Catalogo tipoDato = catalogoServicio.getByTipoAndAcronimo(TipoDatoEnum.PROMEDIO_24H.toString(), CatalogoEnum.TIPO_DATO);
			int nMinimoDatos = Integer.parseInt(configuracionDao.getConfiguracionPorItem("HORAS_DIARIO").getValor());
			String[] arregloHoras = configuracionDao.getConfiguracionPorItem("HORAS_DIARIO").getValor().split(",");
			List<Integer> horas = ListUtils.stringArrayToIntegerList(arregloHoras);
			
			datoDao.calculaPromedios(parametro, tipoDato, horas, fecha, fechaAuxFin, nMinimoDatos, fechaString);
		} catch (Exception e) {
			
		}
		
	}

}
