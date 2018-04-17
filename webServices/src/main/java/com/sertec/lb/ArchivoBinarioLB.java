package com.sertec.lb;

import java.util.Map;

import com.sertec.domain.ArchivoBinario;

public interface ArchivoBinarioLB {
	public boolean hayArchivo(String nombreArchivo, String acronimo);
	public boolean save(ArchivoBinario file, String estacion);
	public Map<String, String> retornoHistoricos(String estacion);
}
