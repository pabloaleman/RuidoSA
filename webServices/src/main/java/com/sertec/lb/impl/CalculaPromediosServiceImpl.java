package com.sertec.lb.impl;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megasoftworks.gl.list.ListUtils;
import com.megasoftworks.gl.manage.Fechas;
import com.megasoftworks.gl.math.Averages;
import com.sertec.beans.CalculaPromediosMensajesListBean;
import com.sertec.daos.ConfiguracionDao;
import com.sertec.daos.DatoCalculadoDao;
import com.sertec.daos.DatoDao;
import com.sertec.domain.Catalogo;
import com.sertec.domain.Dato;
import com.sertec.domain.DatoCalculado;
import com.sertec.domain.Estacion;
import com.sertec.domain.Parametro;
import com.sertec.domain.Usuario;
import com.sertec.enums.CalculaPromedioErrorEnum;
import com.sertec.enums.CatalogoEnum;
import com.sertec.enums.TipoDatoEnum;
import com.sertec.exceptions.CalculaPromediosException;
import com.sertec.exceptions.NoCatalogosException;
import com.sertec.exceptions.NumeroDatosMinimoPromedioException;
import com.sertec.lb.CalculaPromediosService;
import com.sertec.lb.CatalogoServicio;

@Service
@Named("calculaPromediosService")
public class CalculaPromediosServiceImpl implements CalculaPromediosService {
	
	private static final Logger LOGGER = Logger.getLogger(CalculaPromediosServiceImpl.class);
	
	
	@Autowired
	DatoDao datoDao;
	@Autowired
	DatoCalculadoDao datoCalculadoDao;
	@Autowired
	CatalogoServicio catalogoServicio;
	@Autowired
	ConfiguracionDao configuracionDao;
	
	private CalculaPromediosMensajesListBean mensajes;


	@Override
	public CalculaPromediosMensajesListBean calculaPromedios(Date fechaInicio, Date fechaFin, Estacion estacion, List<Parametro> parametrosCP, Usuario usuario) throws CalculaPromediosException {
		fechaInicio = Fechas.enceraHoMiSe(fechaInicio);
		fechaFin = Fechas.enceraHoMiSe(fechaFin);
				
		mensajes = new CalculaPromediosMensajesListBean();
		if(fechaFin.getTime() < fechaInicio.getTime()) {
			throw new CalculaPromediosException("Fecha de inicio mayor que la fecha de fin", CalculaPromedioErrorEnum.FECHA_INICIO_MAYOR_FECHA_FIN);
		} else {
			Long fechaFinLong = fechaFin.getTime();
			Date fechaAux = fechaInicio;
			Catalogo tipoDatoHoraC = catalogoServicio.getCatalogoPromedioHora();
			try {
				Catalogo tipoDato24H = catalogoServicio.getByTipoAndAcronimo(TipoDatoEnum.PROMEDIO_24H.toString(), CatalogoEnum.TIPO_DATO);
				Catalogo tipoDatoDia = catalogoServicio.getByTipoAndAcronimo(TipoDatoEnum.PROMEDIO_DIA.toString(), CatalogoEnum.TIPO_DATO);
				Catalogo tipoDatoNoche = catalogoServicio.getByTipoAndAcronimo(TipoDatoEnum.PROMEDIO_NOCHE.toString(), CatalogoEnum.TIPO_DATO);
				
				//inactivo los datos calculados previamente
				datoCalculadoDao.actualizaPromediosPrevios(estacion, tipoDato24H, fechaInicio, fechaFin);
				datoCalculadoDao.actualizaPromediosPrevios(estacion, tipoDatoDia, fechaInicio, fechaFin);
				datoCalculadoDao.actualizaPromediosPrevios(estacion, tipoDatoNoche, fechaInicio, fechaFin);
				
				
				Date fechaCalculo = new Date();
				while(fechaAux.getTime() <= fechaFinLong) {
					for(Parametro parametro : parametrosCP) {
						calculaPromediosPorTipoDato(fechaAux, parametro, estacion, usuario, tipoDato24H, TipoDatoEnum.PROMEDIO_24H, tipoDatoHoraC, fechaCalculo);
						calculaPromediosPorTipoDato(fechaAux, parametro, estacion, usuario, tipoDatoDia, TipoDatoEnum.PROMEDIO_DIA, tipoDatoHoraC, fechaCalculo);
						calculaPromediosPorTipoDato(fechaAux, parametro, estacion, usuario, tipoDatoNoche, TipoDatoEnum.PROMEDIO_NOCHE, tipoDatoHoraC, fechaCalculo);
					}
					
					fechaAux = Fechas.aumentoDias(fechaAux, 1);
				}
			} catch (NoCatalogosException e) {
				LOGGER.error(e.getMessage());
			}
			
		}
		return mensajes;		
	}
	private void calculaPromediosPorTipoDato(Date fecha, Parametro parametro,
			Estacion estacion, Usuario usuario, Catalogo tipoDato, TipoDatoEnum tipoDatoEnum, Catalogo tipoDatoHoraC, Date fechaCalculo) {
		Date fechaAuxFin = Fechas.finHoMiSe(fecha);
		try {
			
			int nMinimoDatos = Integer.parseInt(configuracionDao.getConfiguracionPorItem(tipoDatoEnum.getNDatosMinimoConf()).getValor());
			String[] arregloHoras = configuracionDao.getConfiguracionPorItem(tipoDatoEnum.getTipoDatoConf()).getValor().split(",");
			List<Integer> horas = ListUtils.stringArrayToIntegerList(arregloHoras);
			calculaPromedios(parametro, tipoDato, horas, fecha, fechaAuxFin, nMinimoDatos, estacion, usuario, tipoDatoHoraC, fechaCalculo);
			
			String mensaje = ResourceBundle.getBundle("mensajes").getString("promedioInsertado");
			mensaje = mensaje.replaceAll("DIA_PROMEDIO", Fechas.dateToString(fecha, "yyyy/MM/dd"))
					.replaceAll("PARAMETRO", parametro.getDescripcion())
					.replaceAll("TIPO_PROMEDIO", tipoDato.getDescripcion());
			mensajes.getMensajesInformacion().add(mensaje);
		} catch (NumeroDatosMinimoPromedioException e) {
			LOGGER.warn(e.getMessage());
			mensajes.getMensajesWarning().add(e.getMessage());
		} catch (CalculaPromediosException e) {
			if(e.getCodigoError() == CalculaPromedioErrorEnum.PERSISTE_BDD_ERROR) {
				LOGGER.error(e.getMessage());
				mensajes.getMensajesError().add(e.getMessage());
			}
		}
		
	}
	
	private void calculaPromedios(Parametro parametro,
			Catalogo tipoDato, List<Integer> horas,
			Date fechaInicio, Date fechaFin, int nMinimoDatos,
			Estacion estacion, Usuario usuario, Catalogo tipoDatoHoraC, Date fechaCarga) throws NumeroDatosMinimoPromedioException, CalculaPromediosException {
		List<Dato> datos = datoDao.getDatosParapromedio(estacion, parametro, tipoDatoHoraC, horas, fechaInicio, fechaFin);
		if(datos.size() >= nMinimoDatos) {
			DatoCalculado datoCalculado = new DatoCalculado();
			datoCalculado.setArchivoDatos(datos.get(0).getArchivoDatos());
			double[] datosArray = new double[datos.size()];
			//calculo los maximos y minimos
			for(int n = 0; n < datos.size(); n++) {
				Dato dato = datos.get(n);
				if(dato.getValor() < datoCalculado.getMinimo()) {
					datoCalculado.setMinimo(dato.getValor());
					datoCalculado.setFechaMinimo(dato.getFecha());
				}
				if(dato.getValor() > datoCalculado.getMaximo()) {
					datoCalculado.setMaximo(dato.getValor());
					datoCalculado.setFechaMaximo(dato.getFecha());
				}
				datosArray[n] = dato.getValor();
			}
			//calculo el promedio
			//datoCalculado.setPromedio(new Mean().evaluate(datosArray));
			datoCalculado.setPromedio(Averages.logAvg(datosArray));
			datoCalculado.setEstacion(estacion);
			datoCalculado.setFecha(fechaInicio);
			datoCalculado.setFechaD(fechaInicio.getTime());
			datoCalculado.setParametro(parametro);
			datoCalculado.setTipoDato(tipoDato);
			datoCalculado.setUsuario(usuario);
			datoCalculado.setFechaCalculo(fechaCarga);
			datoCalculadoDao.persistePromedio(datoCalculado);
		} else {
			String mensaje = ResourceBundle.getBundle("mensajes").getString("nMinimoDatos");
			 mensaje = mensaje.replaceAll("TIPO_PROMEDIO", tipoDato.getDescripcion())
					 .replaceAll("DIA_PROMEDIO", Fechas.dateToString(fechaInicio, "yyyy/MM/dd"))
					 .replaceAll("N_DATOS_HAY", datos.size() + "")
					 .replaceAll("PARAMETRO", parametro.getDescripcion())
					 .replaceAll("N_DATOS_NECESARIOS", nMinimoDatos + "");
			throw new NumeroDatosMinimoPromedioException(mensaje);
		}
		
	}

}
