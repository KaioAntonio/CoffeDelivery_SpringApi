package com.singular.coffedelivery.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido. O formato deve ser XXXXX-XXX")
    @Column(name = "cep")
    private String cep;
    @Column(name = "rua")
    private String rua;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "complemento")
    private String complemento;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "uf")
    private String uf;
}
