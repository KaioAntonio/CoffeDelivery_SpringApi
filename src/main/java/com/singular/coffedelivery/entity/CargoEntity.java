package com.singular.coffedelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity(name = "cargo")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CargoEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cargo")
    private Integer idCargo;

    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @ManyToMany(mappedBy = "cargos")
    private List<UsuarioEntity> usuarios;

    @Override
    public String getAuthority() {
        return nome;
    }
}
