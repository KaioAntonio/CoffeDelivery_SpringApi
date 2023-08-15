package com.singular.coffedelivery.dto.produto;
import lombok.Data;

@Data
public class FileDTO extends ProdutoCreateDTO{

    private byte[] imagem;
}