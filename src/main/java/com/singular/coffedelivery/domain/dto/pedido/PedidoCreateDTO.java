package com.singular.coffedelivery.domain.dto.pedido;

import com.singular.coffedelivery.domain.dto.produto.ProdutoIdQuantidadeDTO;
import com.singular.coffedelivery.domain.entity.ProdutoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoCreateDTO {


    @NotBlank
    @Schema(description = "CEP do Cliente", example = "49700-000")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido. O formato deve ser XXXXX-XXX")
    private String cep;
    @NotBlank
    @Schema(description = "Rua do Cliente", example = "Rua Do José")
    private String rua;
    @NotNull
    @Schema(description = "Número da casa do Cliente", example = "10")
    private Integer numero;
    @Schema(description = "Complemento do Endereço do Cliente", example = "Proximo à Rua da frente")
    private String complemento;
    @NotBlank
    @Schema(description = "Bairro do Cliente", example = "Bairro Nobre")
    private String bairro;
    @NotBlank
    @Schema(description = "Cidade do Cliente", example = "Rio de Janeiro")
    private String cidade;
    @NotBlank
    @Schema(description = "Unidade Federativa", example = "RJ")
    private String uf;
    @Schema(description = "Telefone do Cliente", example = "11999999999" )
    @NotBlank
    private String telefone;
    @Schema(description = "Nome do Cliente", example = "Luan Lemos")
    @NotBlank
    private String nome;
    private List<ProdutoIdQuantidadeDTO> produto;
    @Schema(description = "Valor total", example = "52.99")
    private Double vlTotal;

}
