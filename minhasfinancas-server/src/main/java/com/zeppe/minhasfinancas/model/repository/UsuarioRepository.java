package com.zeppe.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zeppe.minhasfinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	boolean existsByEmail(String email);

}
