package com.singular.coffedelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "cargo")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CargoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @ManyToMany(mappedBy = "cargos")
    private List<UsuarioEntity> usuarios;
}
