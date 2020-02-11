package com.zeppe.minhasfinancas.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.zeppe.minhasfinancas.exceptions.RegraNegocioException;
import com.zeppe.minhasfinancas.model.entity.Usuario;
import com.zeppe.minhasfinancas.model.repository.UsuarioRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Test(expected = Test.None.class)//serves for validating that's method return a exception
	public void deveValidarEmail() {
		//scenario
		usuarioRepository.deleteAll();
		
		//action
		service.validarEmail("teste3@hotmail.com");
		
	}
	
	@Test(expected = RegraNegocioException.class)
	public void deveLancarErrorAoValidarEmailQuandoExisteEmailCadastrado() {
		//scenario
		Usuario usuario = Usuario.builder().nome("teste").senha("teste").email("teste4@hotmail.com").build();
		usuarioRepository.save(usuario);
		
		//action
		service.validarEmail("teste4@hotmail.com");
		
		
	}
	
}
