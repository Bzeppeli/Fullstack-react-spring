package com.zeppe.minhasfinancas.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppe.minhasfinancas.exceptions.RegraNegocioException;
import com.zeppe.minhasfinancas.model.entity.Usuario;
import com.zeppe.minhasfinancas.model.repository.UsuarioRepository;
import com.zeppe.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImp implements UsuarioService {

	private UsuarioRepository usuarioRepository;
	
	@Autowired
	public UsuarioServiceImp(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validarEmail(String Email) {
		boolean existe = usuarioRepository.existsByEmail(Email);
		
		if (existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email");
		}
		
	}

}
