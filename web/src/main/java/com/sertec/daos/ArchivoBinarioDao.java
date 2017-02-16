package com.sertec.daos;

import com.sertec.domain.ArchivoBinario;
import com.sertec.domain.Estacion;

public interface ArchivoBinarioDao {
	public boolean hayArchivo(String nombreArchivo, Estacion estacion);
	public boolean save(ArchivoBinario ab);

}
