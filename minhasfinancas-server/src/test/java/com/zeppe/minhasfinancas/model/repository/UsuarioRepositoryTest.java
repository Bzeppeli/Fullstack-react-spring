package com.zeppe.minhasfinancas.model.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.zeppe.minhasfinancas.model.entity.Usuario;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")//this annotation for add the datasource in memory of properties-test
@DataJpaTest//this annotation add a instance on datasource and after delete it
@AutoConfigureTestDatabase(replace = Replace.NONE)//this annotation is for not desconfigurate the properties-test
public class UsuarioRepositoryTest {
	
	/* 
		it is not interesting that the tested class is instantiated
	 */
	@Autowired
	UsuarioRepository usuarioRepository;
	
	
	@Autowired
	TestEntityManager entityManager; //this is not original entity manager, is only a teste manager
									//configurated for tests, provide by DataJpaTests
	
	/**
	 * Method for create User for usualy avoid repeat code
	 * @return
	 */
	public static Usuario criarUsuario() {
		return Usuario.builder().nome("teste").email("teste@gmail.com").senha("teste").build();
		
	}
	
	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		//Scenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		//action/ execution
		boolean result = usuarioRepository.existsByEmail("teste@gmail.com");
		
		//verification
		Assertions.assertThat(result).isTrue();
		
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverNenhumUsuarioCadastradoComEmail() {
		
		//action/execution
		boolean result = usuarioRepository.existsByEmail("teste2@hotmail.com");
		
		//verification
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		
		//scenario
		Usuario usuario = criarUsuario();
		
		
		//action
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		//verification
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
		
	}
	
	@Test
	public void deveRetornarUsuarioPorEmail() {
		
		//scenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		//action
		Optional<Usuario> result = usuarioRepository.findByEmail("teste@gmail.com");
		
		//verification
		Assertions.assertThat(result).isNotNull();
	}
	
	@Test
	public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExistirNaBase() {
		
		//scenario
	
		
		//action
		Optional<Usuario> result = usuarioRepository.findByEmail("teste@gmail.com");
		
		//verification
		Assertions.assertThat(result.isPresent()).isFalse();
	}
	
	
	
}
