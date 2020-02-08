package com.zeppe.minhasfinancas.service;

import com.zeppe.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String Email);
	
	
}
