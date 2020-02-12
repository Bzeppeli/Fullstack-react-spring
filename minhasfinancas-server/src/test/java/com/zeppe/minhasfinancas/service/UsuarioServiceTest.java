package com.zeppe.minhasfinancas.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.zeppe.minhasfinancas.exceptions.RegraNegocioException;
import com.zeppe.minhasfinancas.model.repository.UsuarioRepository;
import com.zeppe.minhasfinancas.service.imp.UsuarioServiceImp;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	UsuarioService service;
	@MockBean //this annotation causes that with userRepository is mocked
	UsuarioRepository usuarioRepository;
	
	@Before
	public void setUp() {
		service = new UsuarioServiceImp(usuarioRepository);
	}
	
	@Test(expected = Test.None.class)//serves for validating that's method return a exception
	public void deveValidarEmail() {
		//scenario
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//action
		service.validarEmail("teste3@hotmail.com");
		
	}
	
	@Test(expected = RegraNegocioException.class)
	public void deveLancarErrorAoValidarEmailQuandoExisteEmailCadastrado() {
		//scenario
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//action
		service.validarEmail("teste4@hotmail.com");
		
		
	}
	
}
