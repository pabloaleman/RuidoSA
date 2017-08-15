package com.sertec.daos;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sertec.beans.PromedioBean;
import com.sertec.domain.ArchivoDatos;
import com.sertec.domain.Catalogo;
import com.sertec.domain.Dato;
import com.sertec.domain.Parametro;
import com.sertec.exceptions.NumeroDatosMinimoPromedioException;

@Repository
@Transactional
public class DatoDao {
	@PersistenceContext(unitName = "ruidoPU")
    private EntityManager em;
	
	private static final Logger LOGGER = Logger.getLogger(DatoDao.class);
	
	public void persisteDatos(List<Dato> datos, ArchivoDatos archivoDatos, Catalogo tipoDato) {
		for(Dato dato : datos) {
			dato.setArchivoDatos(archivoDatos);
			dato.setUsuario(archivoDatos.getUsuario());
			dato.setEstacion(archivoDatos.getEstacion());
			dato.setTipoDato(tipoDato);
			try {
				List<Dato> datosList = em.createNamedQuery("Dato.findDato", Dato.class)
						.setParameter("parametro", dato.getParametro())
						.setParameter("tipoDato", dato.getTipoDato())
						.setParameter("fechaD", dato.getFechaD())
						.getResultList();
				if(datosList.isEmpty()) {
					em.persist(dato);
					em.flush();
				} else {
					LOGGER.warn("Tratando de registrar dato previamente guardado");
				}
			} catch (Exception e) {
				LOGGER.error("Error guardando el dato");
				LOGGER.error(e.getMessage());
			}
			
		}
	}
	
	public PromedioBean calculaPromedios(Parametro parametro,
			Catalogo tipoDato, List<Integer> horas,
			Date fechaInicio, Date FechaFin, int nMinimoDatos, String fecha) throws NumeroDatosMinimoPromedioException {
		List<Dato> datos= em.createNamedQuery("Dato.findDatosParaPromedio", Dato.class)
				.setParameter("parametro", parametro)
				.setParameter("tipoDato", tipoDato)
				.setParameter("horas", horas)
				.setParameter("fInicio", fechaInicio)
				.setParameter("fFin", FechaFin)
				.getResultList();
		if(datos.size() >= nMinimoDatos) {
			PromedioBean promedioBean = new PromedioBean();
			double[] datosArray = new double[datos.size()];
			//calculo los maximos y minimos
			for(int n = 0; n < datos.size(); n++) {
				Dato dato = datos.get(n);
				if(dato.getValor() < promedioBean.getMinimo()) {
					promedioBean.setMinimo(dato.getValor());
					promedioBean.setHoraMinimo(dato.getFecha());
				}
				if(dato.getValor() > promedioBean.getMaximo()) {
					promedioBean.setMaximo(dato.getValor());
					promedioBean.setHoraMaximo(dato.getFecha());
				}
				datosArray[n] = dato.getValor();
			}
			//calculo el promedio
			promedioBean.setPromedio(new Mean().evaluate(datosArray));
			return promedioBean;
		} else {
			String mensaje = ResourceBundle.getBundle("mensajes").getString("nMinimoDatos");
			 mensaje = mensaje.replaceAll("TIPO_PROMEDIO", tipoDato.getDescripcion())
					 .replaceAll("DIA_PROMEDIO", fecha)
					 .replaceAll("N_DATOS_HAY", datos.size() + "")
					 .replaceAll("N_DATOS_NECESARIOS", nMinimoDatos + "");
			throw new NumeroDatosMinimoPromedioException(mensaje);
		}
		
	}

}
