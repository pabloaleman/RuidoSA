package com.sertec.servicios;

import com.sertec.domain.ArchivoBinario;

public interface ArchivoBinarioLB {
	public boolean hayArchivo(String nombreArchivo, String acronimo);
	public boolean save(ArchivoBinario file, String estacion);
}
