package com.singular.coffedelivery.dto.produto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProdutoCreateDTO {
    @NotBlank
    @Schema(description = "Nome do Produto", example = "Café Expresso")
    private String nome;
    @NotBlank
    @Schema(description = "Descrição do Produto",example = "Expresso Geladinho")
    private String descricao;
    @NotBlank
    @Schema(description = "Tipo do Produto",example = "Gelado")
    private String tipo;
    @NotNull
    @Schema(description = "Preço do Produto",example = "10.90")
    private Double preco;

}