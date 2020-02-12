package com.zeppe.minhasfinancas.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.zeppe.minhasfinancas.exceptions.ErrorAutenticacao;
import com.zeppe.minhasfinancas.exceptions.RegraNegocioException;
import com.zeppe.minhasfinancas.model.entity.Usuario;
import com.zeppe.minhasfinancas.model.repository.UsuarioRepository;
import com.zeppe.minhasfinancas.service.imp.UsuarioServiceImp;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@SpyBean // this annotation allows the original methods in class to be used as a mock
	UsuarioServiceImp service;
	
	@MockBean //this annotation causes that with userRepository is mocked
	UsuarioRepository usuarioRepository;
	
	@Test(expected = Test.None.class)
	public void deveSalvarUmUsuario() {
		//scenario
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder().nome("teste").email("teste@gmail.com").senha("teste").id(1l).build();
		
		Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		//action
		Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
		
		//verification
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("teste");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("teste@gmail.com");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("teste");
		
	}
	
	@Test(expected = RegraNegocioException.class)
	public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
		//scenario
		String email = "teste@gmail.com";
		Usuario usuario = Usuario.builder().email(email).build();
		
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		
		//action
		service.salvarUsuario(usuario);
		
		//verification
		Mockito.verify(usuarioRepository, Mockito.never()).save(usuario);
		
	}
	
	@Test(expected = Test.None.class)
	public void deveAutenticarUmUsuarioComSucesso() {
		
		//scenario
		String email = "teste@email.com";
		String senha = "teste";
		
		Usuario usuario = Usuario.builder().nome("teste").email(email).senha(senha).id(1l).build();
		Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		//action
		Usuario result = service.autenticar(email, senha);
		
		//verification
		Assertions.assertThat(result).isNotNull();
		
		
	}
	
	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
		
		//scenario
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		//Action
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("teste@gmail.com", "teste"));
		
		//verification
		Assertions.assertThat(exception).isInstanceOf(ErrorAutenticacao.class).hasMessage("Usuario não Encontrado");
	}
	
	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		
		//scenario
		String senha = "teste";
		Usuario usuario = Usuario.builder().nome("teste").email("teste@gmail.com").senha(senha).build();
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		
		//Action
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("teste@gmail.com", "testef") );
		
		//verification
		Assertions.assertThat(exception).isInstanceOf(ErrorAutenticacao.class).hasMessage("Senha incorreta");
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
