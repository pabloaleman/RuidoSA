package com.sertec.lb;

import java.util.List;

import com.sertec.domain.ArchivoDatos;
import com.sertec.domain.Catalogo;
import com.sertec.domain.Dato;

public interface DatoServicio {
	public void persisteDatos(List<Dato> datos, ArchivoDatos archivoDatos, Catalogo tipoDato);

}
