package com.zeppe.minhasfinancas.api.DTO;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LancamentoDTO {
	
	private Long id;
	private String descricao;
	private Integer mes;
	private Integer ano;
	private BigDecimal valor;
	private Long idUsuario;
	private String tipo;
	private String status;

}
