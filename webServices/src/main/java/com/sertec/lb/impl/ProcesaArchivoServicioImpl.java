package com.sertec.lb.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.megasoftworks.gl.manage.Fechas;
import com.sertec.beans.DatoIndexArchivo;
import com.sertec.beans.PreprocesoArchivoBean;
import com.sertec.domain.ArchivoDatos;
import com.sertec.domain.Catalogo;
import com.sertec.domain.Dato;
import com.sertec.domain.Estacion;
import com.sertec.domain.MensajesDeError;
import com.sertec.domain.Parametro;
import com.sertec.domain.Usuario;
import com.sertec.enums.CatalogoEnum;
import com.sertec.exceptions.CargaArchivoException;
import com.sertec.exceptions.CargaDatosException;
import com.sertec.lb.ArchivoDatosServicio;
import com.sertec.lb.CatalogoServicio;
import com.sertec.lb.DatoServicio;
import com.sertec.lb.MensajesServicio;
import com.sertec.lb.ParametroServicio;
import com.sertec.lb.ProcesaArchivoServicio;

@Component
@Named("procesaArchivoServicio")
public class ProcesaArchivoServicioImpl implements ProcesaArchivoServicio {
	@Autowired
	MensajesServicio mensajesServicio;
	@Autowired
	ParametroServicio parametroServicio;
	@Autowired
	DatoServicio datoServicio;
	@Autowired
	ArchivoDatosServicio archivoDatosServicio;
	@Autowired
	CatalogoServicio catalogoServicio;
	
	private Map<String, Parametro> mapaParametros;
	private List<DatoIndexArchivo> parametrosEnArchivo = new ArrayList<>();
	private Catalogo plataforma1;
	
	private void cargaParams() {
		mapaParametros = parametroServicio.getMapaParametros();
		//TODO cambiar esto
		try {
			List<Catalogo> plataformas = catalogoServicio.getByTipo(CatalogoEnum.PLATAFORMA);
			plataforma1 = plataformas.get(0);
		} catch (Exception e) {
			System.out.println("Error al obtener las plataformas");
		}	
	}
	
	@Override
	public void reiniciaBean() {
		parametrosEnArchivo = new ArrayList<>();
	}

	@Override
	public PreprocesoArchivoBean procesaArchivo(String contenido, String formatoFecha) throws CargaArchivoException {
		cargaParams();
		PreprocesoArchivoBean preprocesoArchivoBean = new PreprocesoArchivoBean();
		String[] lineas = contenido.split("\n");
		if(lineas.length < 5) {
			MensajesDeError mensajesDeError = mensajesServicio.getMensajeByAcronimo("error0002");
			throw new CargaArchivoException(mensajesDeError.getDescripcion());
		}
		
		boolean cabecaraFecha = false;
		boolean cabeceraMedida = false;
		boolean cabeceraPerfil = false;
		boolean cabeceraCanal = false;
		int lineaCanal = -1;
		int lineaPerfil = -1;
		int lineaMedida = -1;
		int lineaNivel = -1;
		int lineaFecha = -1;
		for(int nLinea = 0; nLinea < lineas.length; nLinea++) {
			String linea = lineas[nLinea];
			if(linea.contains("Elapsed")) {
				cabeceraMedida = true;
				lineaMedida= nLinea;
			}
			if(linea.contains("Date")) {
				cabecaraFecha = true;
				lineaFecha = nLinea;
				//la linea de nivel es la misma linea de la fecha
				lineaNivel = nLinea;
			}
			String[] columnasDatos = linea.split(";");
			for(String columnaDato : columnasDatos) {
				if(!cabeceraCanal && columnaDato != null && !columnaDato.isEmpty() && columnaDato.startsWith("Ch")) {
					lineaCanal = nLinea;
					cabeceraCanal = true;
					break;
				}
				
				if(!cabeceraPerfil && columnaDato != null && !columnaDato.isEmpty() && columnaDato.startsWith("P")) {
					lineaPerfil = nLinea;
					cabeceraPerfil = true;
					break;
				}
				
			}
			if(cabeceraMedida && cabecaraFecha && cabeceraPerfil && cabeceraCanal) {
				break;
			}
		}
		//reviso que hayan todas las cabeceras
		if(!cabeceraMedida || !cabecaraFecha || !cabeceraPerfil || !cabeceraCanal) {
			MensajesDeError mensajesDeError = mensajesServicio.getMensajeByAcronimo("error0002");
			throw new CargaArchivoException(mensajesDeError.getDescripcion());
		}
		
		//reviso que hayan datos en el archivo
		if(lineaFecha == lineas.length) {
			MensajesDeError mensajesDeError = mensajesServicio.getMensajeByAcronimo("error0001");
			throw new CargaArchivoException(mensajesDeError.getDescripcion());
		}
		
		String medidasLineaCruda = lineas[lineaMedida].trim().replace(" ", "").replace("\t", "").toUpperCase();
		String perfilesLineaCruda = lineas[lineaPerfil].trim().replace(" ", "").replace("\t", "").toUpperCase();
		String canalesLineaCruda = lineas[lineaCanal].trim().replace(" ", "").replace("\t", "").toUpperCase();
		String nivelLineaCruda = lineas[lineaNivel].trim().replace(" ", "").replace("\t", "").toUpperCase();
		
		String[] medidasArreglo = medidasLineaCruda.split(";");
		String[] perfilesArreglo = perfilesLineaCruda.split(";");
		String[] canalesArreglo = canalesLineaCruda.split(";");
		String[] nivelesArreglo = nivelLineaCruda.split(";");
		
		//armo la cabecera
		int columnaFecha = -1;
		//reviso columna date
		String[] datosLineaFecha = lineas[lineaFecha].toUpperCase().split(";");
		for(int n = 0; n < datosLineaFecha.length; n++) {
			String dato = datosLineaFecha[n];
			if(dato != null && dato.contains("DATE")) {
				columnaFecha = n;
				break;
			}
		}
		
		if(lineaFecha == -1 || columnaFecha == -1) {
			MensajesDeError mensajesDeError = mensajesServicio.getMensajeByAcronimo("error0002");
			throw new CargaArchivoException(mensajesDeError.getDescripcion());
		}

		//armo los parametros
		for(int n = 0; n < medidasArreglo.length; n++) {
			String medida = medidasArreglo[n];
			String acronimoArmado;
			if(medida.length() > 0
					&& !medida.contains("Filename")
					&& !medida.contains("Elapsed")) {
				acronimoArmado = canalesArreglo[n] + perfilesArreglo[n] + medida + nivelesArreglo[n];
				Parametro param = mapaParametros.get(acronimoArmado);
				if(null != param) {
					DatoIndexArchivo dia = new DatoIndexArchivo();
					dia.setParametro(param);
					dia.setIndex(n);
					parametrosEnArchivo.add(dia);
					preprocesoArchivoBean.getParametroscargar().add(param);
				} else {
					Parametro parametroAux = new Parametro();
					
					parametroAux.setCanal(new Catalogo(canalesArreglo[n]));
					parametroAux.setPerfil(new Catalogo(perfilesArreglo[n]));
					parametroAux.setMedida(new Catalogo(medida));
					parametroAux.setNivel(new Catalogo(nivelesArreglo[n]));
					preprocesoArchivoBean.getParametrosNoBdd().add(parametroAux);
				}
			}
		}
		
		if(parametrosEnArchivo.isEmpty()) {
			MensajesDeError mensajesDeError = mensajesServicio.getMensajeByAcronimo("error0002");
			throw new CargaArchivoException(mensajesDeError.getDescripcion());
		}
		
		// procedo a cargar los datos
		for(int nLinea = lineaFecha + 1; nLinea < lineas.length; nLinea++) {
			String[] datosLinea = lineas[nLinea].split(";");
			for(DatoIndexArchivo dia : parametrosEnArchivo) {
				Dato dato = new Dato();
				dato.setParametro(dia.getParametro());
				dato.setValor(Double.parseDouble(datosLinea[dia.getIndex()]));
				dato.setFecha(Fechas.stringToDate(
						datosLinea[columnaFecha],
						formatoFecha));
				dato.setFechaD(dato.getFecha().getTime());
				int hora = Integer.parseInt(Fechas.dateToString(dato.getFecha(), "HH"));
				dato.setHora(hora);
				preprocesoArchivoBean.getDatos().add(dato);
				if(dia.getParametro().isPrincipal()) {
					preprocesoArchivoBean.getDatosPrincipales().add(dato);
				}
				if(dia.getParametro().isGuarda()) {
					preprocesoArchivoBean.getDatosPersistir().add(dato);
				}
			}
			
			
		}
		return preprocesoArchivoBean;
	}
	
	@Override
	public void persisteDatos(ArchivoDatos archivoDatos, Estacion estacion, Usuario usuario, List<Dato> datos) throws CargaDatosException {
		
	}

	@Override
	public void persisteDatos(PreprocesoArchivoBean preprocesoArchivoBean, String nombreArchivo, Estacion estacion, Usuario usuario, Catalogo tipoDato) {
		ArchivoDatos archivoDatos = new ArchivoDatos();
		archivoDatos.setNombre(nombreArchivo);
		archivoDatos.setUsuario(usuario);
		archivoDatos.setEstacion(estacion);
		archivoDatos.setPlataforma(plataforma1);
		archivoDatosServicio.persisteArchivo(archivoDatos);
		datoServicio.persisteDatos(preprocesoArchivoBean.getDatosPersistir(), archivoDatos, tipoDato);
	}

}
