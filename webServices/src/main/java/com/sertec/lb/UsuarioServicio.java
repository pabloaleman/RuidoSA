package com.sertec.lb;

import com.sertec.domain.Usuario;
import com.sertec.exceptions.NoUsuarioEncontradoException;

public interface UsuarioServicio {
	public Usuario findByUserName(String username) throws NoUsuarioEncontradoException;
}
