package com.singular.coffedelivery.domain.dto.produto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProdutoIdQuantidadeDTO {

    @Schema(description = "Quantidade de Produtos", example = "10")
    @NotNull
    private Integer qtProdutos;
    @Schema(description = "Produto")
    private Integer idProduto;
}
