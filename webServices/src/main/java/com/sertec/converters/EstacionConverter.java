package com.sertec.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sertec.daos.EstacionDao;
import com.sertec.domain.Estacion;

@Component
@FacesConverter("estacionConverter")
public class EstacionConverter  implements Converter {
	private static final Logger LOGGER = Logger.getLogger(EstacionConverter.class);
	
	@Autowired
	EstacionDao estacionDao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		LOGGER.info("Convirtiendo valor: " + value);
		if(value == null || value.equals("")) {
			return null;
		}
		return estacionDao.getById(Long.parseLong(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null) {
			return "";
		}
			
		Estacion estacion = (Estacion)value;
		LOGGER.info("Convirtiendo valor: " + estacion.getAcronimo());
		return "" + estacion.getId();
	}
	
}