package com.singular.coffedelivery.util.enums;

public enum Situacao {
    ATIVO("Ativo"),
    INATIVO("Inativo");

    private  final String descricao;

    private Situacao(String descricao){
        this.descricao = descricao;
    }

    private String getDescricao() {
        return descricao;
    }
}
