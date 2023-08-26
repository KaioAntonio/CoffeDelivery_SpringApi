package com.singular.coffedelivery.domain.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Embeddable
@Data
public class DadosCliente {

    @Pattern(regexp = "\\d{12}", message = "Telefone inválido. Deve conter 10 dígitos.")
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "nomeCliente")
    private String nome;
}
