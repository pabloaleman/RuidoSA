package com.sertec.lb;

import java.util.List;

import com.sertec.beans.PreprocesoArchivoBean;
import com.sertec.domain.ArchivoDatos;
import com.sertec.domain.Catalogo;
import com.sertec.domain.Dato;
import com.sertec.domain.Estacion;
import com.sertec.domain.Usuario;
import com.sertec.exceptions.CargaArchivoException;
import com.sertec.exceptions.CargaDatosException;

public interface ProcesaArchivoServicio {
	public PreprocesoArchivoBean procesaArchivo(String contenido, String formatoFecha) throws CargaArchivoException;
	public void persisteDatos(ArchivoDatos archivoDatos, Estacion estacion, Usuario usuario, List<Dato> datos) throws CargaDatosException;
	public void persisteDatos(PreprocesoArchivoBean preprocesoArchivoBean, String nombreArchivo, Estacion estacion, Usuario usuario, Catalogo tipoDato);
	public void reiniciaBean();
}
