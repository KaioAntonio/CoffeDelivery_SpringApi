package com.singular.coffedelivery.domain.dto.pedido;

import com.singular.coffedelivery.util.enums.Situacao;
import lombok.Data;

@Data
public class PedidoDTO extends PedidoCreateDTO {
    private Integer idPedido;
    private Double vlTotal;
    private Situacao situacao;
}
