package com.sertec.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sertec.daos.CatalogoDao;
import com.sertec.domain.Catalogo;
import com.sertec.enums.CatalogoEnum;

@Component
@FacesConverter("catalogoConverter")
public class CatalogoConverter implements Converter {
	private static final Logger LOGGER = Logger.getLogger(CatalogoConverter.class);
	
	@Autowired
	CatalogoDao catalogoDao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		LOGGER.info("Convirtiendo valor: " + value);
		if(value == null || value.equals("")) {
			return null;
		}
		if(value.equals("-1")) {
			Catalogo cat = new Catalogo();
			cat.setAcronimo("NOAPLICA");
			cat.setDescripcion("Nivel de n aplica");
			cat.setId(-1L);
			cat.setTipo(CatalogoEnum.NIVEL.getNombre());
			return cat;
		}
		Catalogo cat = catalogoDao.getById(Long.parseLong(value));
		LOGGER.info(cat);
		return cat;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null) {
			return "";
		}
			
		Catalogo catalogo = (Catalogo)value;
		LOGGER.info("Convirtiendo valor: " + catalogo.getAcronimo());
		return "" + catalogo.getId();
	}
	
}
