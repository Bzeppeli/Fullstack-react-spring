package com.zeppe.minhasfinancas.service;

import java.util.List;

import com.zeppe.minhasfinancas.model.entity.Lancamento;
import com.zeppe.minhasfinancas.model.entity.enuns.StatusLancamento;

public interface LancamentoService {
	
	Lancamento salvar(Lancamento lancamento);
	
	Lancamento autualizar(Lancamento lancamento);
	
	void deletar(Lancamento lancamento);
	
	List<Lancamento> buscar (Lancamento lancamentoFiltro);
	
	void atualizarStatus (Lancamento lancamento, StatusLancamento status);
	

}
