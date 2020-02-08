package com.zeppe.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zeppe.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
