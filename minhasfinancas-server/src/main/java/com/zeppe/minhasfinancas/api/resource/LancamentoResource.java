package com.zeppe.minhasfinancas.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zeppe.minhasfinancas.api.DTO.LancamentoDTO;
import com.zeppe.minhasfinancas.exceptions.RegraNegocioException;
import com.zeppe.minhasfinancas.model.entity.Lancamento;
import com.zeppe.minhasfinancas.model.entity.Usuario;
import com.zeppe.minhasfinancas.model.entity.enuns.StatusLancamento;
import com.zeppe.minhasfinancas.model.entity.enuns.TipoLancamento;
import com.zeppe.minhasfinancas.service.LancamentoService;
import com.zeppe.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {
	
	private LancamentoService lancamentoService;
	private UsuarioService usuarioService;
	
	public LancamentoResource(LancamentoService lancamentoService) {
		this.lancamentoService = lancamentoService;
	}
	
	private Lancamento converter(LancamentoDTO dto) {
		Lancamento lancamento = new Lancamento();
		
		lancamento.setId(dto.getId());
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setAno(dto.getAno());
		lancamento.setMes(dto.getMes());
		lancamento.setValor(dto.getValor());
		
		Usuario usuario = usuarioService.obterPorId(dto.getIdUsuario())
					  .orElseThrow( () -> new RegraNegocioException("Usuário não encontrado para o id informado"));
		lancamento.setUsuario(usuario);
		lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
		
		return lancamento;
	}
	
	@PostMapping
	public ResponseEntity salvar( @RequestBody LancamentoDTO dto) {
		
		try {
			Lancamento entidade = converter(dto);
			entidade = lancamentoService.salvar(entidade);
			
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@GetMapping
	public ResponseEntity Listar(
								@RequestParam(value = "descricao", required=false) String descricao,
								@RequestParam(value = "mes", required=false) Integer mes,
								@RequestParam(value = "ano", required=false) Integer ano,
								@RequestParam("usuario") Long idUsuario
								) {
		Lancamento lancamentoFiltro = new Lancamento();
		lancamentoFiltro.setDescricao(descricao);
		lancamentoFiltro.setMes(mes);
		lancamentoFiltro.setAno(ano);
		
		Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
		if(!usuario.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Usuário não encontrado para o id informado");
		}else {
			lancamentoFiltro.setUsuario(usuario.get());
		}
		
		List<Lancamento> lancamentos = lancamentoService.buscar(lancamentoFiltro);
		return ResponseEntity.ok(lancamentos);
	}
	
	@PutMapping("{id}")
	public ResponseEntity Atualizar( @PathVariable("id") Long id, @RequestBody LancamentoDTO dto ) {
		return	lancamentoService.obterPorId(id).map(entity -> {
					try {
						Lancamento lancamento = converter(dto);
						lancamento.setId(entity.getId());
						lancamentoService.atualizar(lancamento);
						return ResponseEntity.ok(lancamento);
					} catch (RegraNegocioException e) {
						return ResponseEntity.badRequest().body(e.getMessage());
					}
				}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados", HttpStatus.BAD_REQUEST));	
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar( @PathVariable("id") Long id) {
		return lancamentoService.obterPorId(id).map(entity -> {
			lancamentoService.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados", HttpStatus.BAD_REQUEST));
	}

}
