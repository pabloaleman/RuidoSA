package com.sertec.spring;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger LOGGER = Logger.getLogger(MyAuthenticationFailureHandler.class);
	@Override
	public void onAuthenticationFailure(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2)
			throws IOException, ServletException {
		LOGGER.error(arg2.getMessage());
		arg0.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, arg2);
		
		arg1.sendRedirect("login.xhtml");
		
	}

}
