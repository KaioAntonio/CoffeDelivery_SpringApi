package com.singular.coffedelivery.domain.dto.produto;
import lombok.Data;

@Data
public class FileDTO extends ProdutoCreateDTO{

    private byte[] imagem;
}