package com.singular.coffedelivery.dto.produto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProdutoCreateDTO {
    @NotBlank
    @Schema(description = "Café Expresso")
    private String nome;
    @NotBlank
    @Schema(description = "Expresso Geladinho")
    private String descricao;
    @NotBlank
    @Schema(description = "Gelado")
    private String tipo;
    @NotBlank
    @Schema(description = "10.90")
    private Double preco;

}