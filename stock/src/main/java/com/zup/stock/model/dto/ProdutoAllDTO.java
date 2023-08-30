package com.zup.stock.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ProdutoAllDTO {

    @NotEmpty(message = "Nome do produto não pode ser vazio.")
    private String nome;

    @NotEmpty(message = "Descrição do produto não pode ser vazia.")
    private String descricao;

    @DecimalMin(value = "0.05", message = "Preço deve ser maior ou igual a 0.05")
    private BigDecimal preco;

    @Min(value = 1, message = "Quantidade deve ser maior ou igual a 0.")
    private Integer quantidade;
}
