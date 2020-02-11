package com.zeppe.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.zeppe.minhasfinancas.model.entity.Usuario;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")//Anotation para acionar o bd em memoria no properties-test
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		//Scenario
		Usuario usuario = Usuario.builder().nome("usuario").email("teste@hotmail.com").senha("senha").build();
		usuarioRepository.save(usuario);
		
		//action/ execution
		boolean result = usuarioRepository.existsByEmail("teste@hotmail.com");
		
		//verification
		Assertions.assertThat(result).isTrue();
		
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverNenhumUsuarioCadastradoComEmail() {
		//scenario
		usuarioRepository.deleteAll();
		
		//action/execution
		boolean result = usuarioRepository.existsByEmail("teste2@hotmail.com");
		
		//verification
		Assertions.assertThat(result).isFalse();
	}
	
}
