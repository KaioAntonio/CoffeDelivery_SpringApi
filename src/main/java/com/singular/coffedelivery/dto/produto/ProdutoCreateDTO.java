package com.singular.coffedelivery.dto.produto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ProdutoCreateDTO {
    @NotBlank
    @Schema(description = "Nome do Produto", example = "Café Expresso")
    @Size(max = 255)
    private String nome;
    @NotBlank
    @Schema(description = "Descrição do Produto",example = "Expresso Geladinho")
    @Size(max = 255)
    private String descricao;
    @NotBlank
    @Schema(description = "Tipo do Produto",example = "Gelado")
    @Size(max = 255)
    private String tipo;
    @NotNull
    @Schema(description = "Preço do Produto",example = "10.90")
    @DecimalMin(value = "0.01", inclusive = true)
    @DecimalMax(value = "9999.99")
    private Double preco;

}