package com.zeppe.minhasfinancas.service.imp;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeppe.minhasfinancas.exceptions.RegraNegocioException;
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
		validar(lancamento);
		lancamento.setStatus(StatusLancamento.PENDENTE);
		return lancamentoRepository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		Objects.nonNull(lancamento.getId());
		validar(lancamento);
		return lancamentoRepository.save(lancamento);
	}

	@Override
	public void deletar(Lancamento lancamento) {
		Objects.nonNull(lancamento.getId());
		lancamentoRepository.delete(lancamento);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	/*
	 *this interface is used to create an instance of examples of 
	 *the parameters so that you can filter only the attributes that were populated
	 */
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		Example example = Example.of( lancamentoFiltro, ExampleMatcher.matching()
														.withIgnoreCase()
														.withStringMatcher(StringMatcher.CONTAINING) ); 
		return lancamentoRepository.findAll(example);
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		lancamento.setStatus(status);
		atualizar(lancamento);
		
	}

	@Override
	public void validar(Lancamento lancamento) {
		
		if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma descrição válida.");
		}
		
		if(lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12) {
			throw new RegraNegocioException("Informe um Mês Valido");
		}
		
		if(lancamento.getAno() == null || lancamento.getAno().toString().length() != 4) {
			throw new RegraNegocioException("Informe um ano válido");
		}else if(lancamento.getAno() < 1900){
			throw new RegraNegocioException("Só é permitidos lancamentos apartir de 1900");
		}
		
		if(lancamento.getUsuario() == null ) {
			throw new RegraNegocioException("Informe um Usuário");
		}
		
		if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO)<1) {
			throw new RegraNegocioException("Informe um valor Válido");
		}
		
		if(lancamento.getStatus() == null) {
			throw new RegraNegocioException("Informe um tipo de lançamento");
		}
		
	}

	@Override
	public Optional<Lancamento> obterPorId(Long id) {
		return lancamentoRepository.findById(id);
	}

}
