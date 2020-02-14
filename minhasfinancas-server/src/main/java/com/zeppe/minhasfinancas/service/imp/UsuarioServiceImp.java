package com.zeppe.minhasfinancas.service.imp;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppe.minhasfinancas.exceptions.ErrorAutenticacao;
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
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new ErrorAutenticacao("Usuario não Encontrado");
		}
		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErrorAutenticacao("Senha incorreta");
		}
		
		return usuario.get();
	}

	@Override
	@Transactional//this annotation'll do a transaction on datasource and will do commit
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return usuarioRepository.save(usuario);
	}

	@Override
	public void validarEmail(String Email) {
		boolean existe = usuarioRepository.existsByEmail(Email);
		
		if (existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email");
		}
		
	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
		return usuarioRepository.findById(id);
	}

}
