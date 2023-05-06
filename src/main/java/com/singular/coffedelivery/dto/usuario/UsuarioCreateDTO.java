package com.singular.coffedelivery.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UsuarioCreateDTO {
    @NotNull
    @NotBlank
    @Schema(description = "Nome do Usuario" , example = "usuario")
    private String nome;

    @NotNull
    @NotBlank
    @Schema(description = "Email do Usuario" , example = "usuario@gmail.com")
    private String email;

    @NotNull
    @NotBlank
    @Schema(description = "Senha do Usuario" , example = "senha1234")
    private String senha;
}
