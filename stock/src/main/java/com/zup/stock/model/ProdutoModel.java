package com.zup.stock.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_PRODUTOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Nome do produto não pode ser vazio.")
    @Schema(description = "Nome do produto", example = "Arroz Integral")
    private String nome;

    @Column(nullable = false)
    @NotEmpty(message = "Descrição do produto não pode ser vazia.")
    @Schema(description = "Descrição do produto", example = "Arroz integral solito")
    private String descricao;

    @Column(nullable = false)
    @DecimalMin(value = "0.05", message = "Preço deve ser maior ou igual a 0.05")
    @Schema(description = "Preço do produto", example = "13.90")
    private BigDecimal preco;

    @Column(nullable = false)
    @Min(value = 1, message = "Quantidade deve ser maior que 0.")
    @Schema(description = "Quantidade do produto", example = "15")
    private Integer quantidade;
}
