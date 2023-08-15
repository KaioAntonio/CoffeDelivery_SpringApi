package com.singular.coffedelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.singular.coffedelivery.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "produto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "situacao")
    private Situacao situacao;

    @Column(name = "dtCriacao")
    private LocalDateTime dtCriacao;

    @JsonIgnore
    @OneToOne(mappedBy = "produto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FileEntity file;

}
