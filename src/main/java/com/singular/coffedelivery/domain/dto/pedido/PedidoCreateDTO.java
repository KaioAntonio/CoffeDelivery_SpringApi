package com.singular.coffedelivery.domain.dto.pedido;

import com.singular.coffedelivery.domain.dto.produto.ProdutoIdQuantidadeDTO;
import com.singular.coffedelivery.util.enums.FormaPagamento;
import com.singular.coffedelivery.util.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PedidoCreateDTO {


    @NotBlank
    @Schema(description = "CEP do Cliente", example = "49700-000")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido. O formato deve ser XXXXX-XXX")
    private String cep;
    @NotBlank @Size(max = 255)
    @Schema(description = "Rua do Cliente", example = "Rua Do José")
    private String rua;
    @NotNull @Max(9999)
    @Schema(description = "Número da casa do Cliente", example = "10")
    private Integer numero;
    @Size(max = 255)
    @Schema(description = "Complemento do Endereço do Cliente", example = "Proximo à Rua da frente")
    private String complemento;
    @NotBlank @Size(max = 255)
    @Schema(description = "Bairro do Cliente", example = "Bairro Nobre")
    private String bairro;
    @NotBlank @Size(max = 255)
    @Schema(description = "Cidade do Cliente", example = "Rio de Janeiro")
    private String cidade;
    @NotBlank @Size(max = 2)
    @Schema(description = "Unidade Federativa", example = "RJ")
    private String uf;
    @Schema(description = "Telefone do Cliente", example = "11999999999" )
    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\) \\d \\d{4}-\\d{4}", message = "Número inválido. O formato deve ser (XX) X XXXX-XXXX")
    private String telefone;
    @Schema(description = "Nome do Cliente", example = "Luan Lemos")
    @NotBlank @Size(max = 250)
    private String nome;
    @Schema(description = "Forma de Pagamento", example = "DINHEIRO")
    @NotNull
    private FormaPagamento formaPagamento;
    @Schema(description = "Situação do Pedido", example = "ENVIADO")
    @NotNull
    private Situacao situacao;
    private List<ProdutoIdQuantidadeDTO> produto;

}
