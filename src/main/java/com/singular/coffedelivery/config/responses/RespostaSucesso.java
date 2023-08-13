package com.singular.coffedelivery.config.responses;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RespostaSucesso {

    private String mensagem;
    private LocalDateTime data;
    private Object dados;

    public RespostaSucesso (Object dados) {
        this.mensagem = "Requisição feita com suceso!";
        this.data = LocalDateTime.now();
        this.dados = dados;
    }

}
