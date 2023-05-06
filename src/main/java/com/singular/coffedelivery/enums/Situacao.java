package com.singular.coffedelivery.enums;

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
