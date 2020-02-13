package com.zeppe.minhasfinancas.service.imp;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeppe.minhasfinancas.model.entity.Lancamento;
import com.zeppe.minhasfinancas.model.entity.enuns.StatusLancamento;
import com.zeppe.minhasfinancas.model.repository.LancamentoRepository;
import com.zeppe.minhasfinancas.service.LancamentoService;

@Service
public class LancamentoServiceImp implements LancamentoService {

	private LancamentoRepository lancamentoRepository;
	
	public LancamentoServiceImp(LancamentoRepository lancamentoRepository) {
		this.lancamentoRepository = lancamentoRepository;
	}
	
	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		return lancamentoRepository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento autualizar(Lancamento lancamento) {
		Objects.nonNull(lancamento.getId());
		return lancamentoRepository.save(lancamento);
	}

	@Override
	public void deletar(Lancamento lancamento) {
		Objects.nonNull(lancamento.getId());
		lancamentoRepository.delete(lancamento);
		
	}

	@Override
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarLancameto(Lancamento lancamento, StatusLancamento status) {
		// TODO Auto-generated method stub
		
	}

}
