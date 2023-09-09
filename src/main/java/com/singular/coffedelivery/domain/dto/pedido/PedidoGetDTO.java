package com.singular.coffedelivery.domain.dto.pedido;

import com.singular.coffedelivery.domain.entity.ProdutoEntity;
import com.singular.coffedelivery.domain.vo.DadosCliente;
import com.singular.coffedelivery.domain.vo.Endereco;
import com.singular.coffedelivery.util.enums.FormaPagamento;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PedidoGetDTO {
    private Integer idPedido;
    private Double vlTotal;
    private LocalDateTime dataPedido;
    private Endereco endereco;
    private DadosCliente dadosCliente;
    private FormaPagamento formaPagamento;
    private Set<ProdutoEntity> produtos;
}
