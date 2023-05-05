package com.singular.coffedelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "files")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFile")
    private Integer idFile;

    @Column(name = "name")
    private String name;

    @Column(name = "tipo")
    private String type;

    @Column(name = "dado")
    @Lob
    private byte[] data;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID_PRODUTO")
    public ProdutoEntity produtoEntity;

}
