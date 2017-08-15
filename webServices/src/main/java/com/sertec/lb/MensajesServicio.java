package com.sertec.lb;

import java.util.List;

import com.sertec.domain.MensajesDeError;

public interface MensajesServicio {
	public List<MensajesDeError> getMensajes();
	public MensajesDeError getMensajeByAcronimo(String acronimo);
}
