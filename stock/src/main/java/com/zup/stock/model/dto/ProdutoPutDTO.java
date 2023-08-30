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
public class ProdutoPutDTO {

    @NotEmpty(groups = AtributosParciaisUpdate .class, message = "Nome do produto não pode ser vazio.")
    private String nome;

    @NotEmpty(groups = AtributosParciaisUpdate .class, message = "Descrição do produto não pode ser vazia.")
    private String descricao;

    @DecimalMin(groups = AtributosParciaisUpdate .class, value = "0.05", message = "Preço deve ser maior ou igual a 0.05")
    private BigDecimal preco;

    @Min(groups = AtributosParciaisUpdate .class, value = 1, message = "Quantidade deve ser maior ou igual a 0.")
    private Integer quantidade;
}
