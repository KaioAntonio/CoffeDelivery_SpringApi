package com.singular.coffedelivery.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.singular.coffedelivery.domain.vo.DadosCliente;
import com.singular.coffedelivery.domain.vo.Endereco;
import com.singular.coffedelivery.util.enums.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PEDIDO")
    private Integer idPedido;

    @Column(name = "dataPedido")
    private LocalDateTime dataPedido;

    @Column(name = "quantidadeProdutos")
    private Integer qtProdutos;

    @Column(name = "valorTotal")
    private Double vlTotal;

    @Column(name = "endereco")
    private Endereco endereco;

    @Column(name = "dadosCliente")
    private DadosCliente dadosCliente;

    @Column(name = "formaPagamento")
    private FormaPagamento formaPagamento;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "pedido_produto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private Set<ProdutoEntity> produtos = new HashSet<>();
}
