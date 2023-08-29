package com.zup.stock.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidade;
}
