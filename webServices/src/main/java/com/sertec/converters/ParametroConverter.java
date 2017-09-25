package com.sertec.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sertec.daos.ParametroDao;
import com.sertec.domain.Parametro;

@Component
@FacesConverter("parametroConverter")
public class ParametroConverter implements Converter {
	private static final Logger LOGGER = Logger.getLogger(ParametroConverter.class);
	
	@Autowired
	ParametroDao parametroDao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		LOGGER.info("Convirtiendo valor: " + value);
		if(value == null || value.equals("")) {
			return null;
		}
		
		Parametro parametro = parametroDao.getById(Long.parseLong(value));
		LOGGER.info(parametro);
		return parametro;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null) {
			return "";
		}
			
		Parametro parametro = (Parametro)value;
		LOGGER.info("Convirtiendo valor: " + parametro.getAcronimo());
		return "" + parametro.getId();
	}
	
}
