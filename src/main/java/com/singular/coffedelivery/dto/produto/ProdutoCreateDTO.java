package com.singular.coffedelivery.dto.produto;

import com.singular.coffedelivery.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class ProdutoCreateDTO {

    private Integer idProduto;
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
    @NotNull
    @Schema(description = "Situação do Produto",example = "0")
    @Size(max = 1)
    private Situacao situacao;
    @NotNull
    @Schema(description = "Data de criação do Produto",example = "13/05/2022 20:00")
    private LocalDateTime dtCriacao;
    @NotNull
    @Schema(description = "Quantidade de estoque do Produto", example = "1")
    private Integer qtProduto;

}