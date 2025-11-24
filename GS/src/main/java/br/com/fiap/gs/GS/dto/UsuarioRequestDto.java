package br.com.fiap.gs.GS.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record UsuarioRequestDto(
        @NotBlank(message = "O username é obrigatório")
        String username,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "O nome completo é obrigatório")
        String nomeCompleto,

        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        @NotBlank(message = "A senha é obrigatória")
        String senha, // ⚡ renomeado para bater com o template


        Boolean enabled,


        List<Long>  rolesIds // IDs das roles
) {
        public UsuarioRequestDto() {
                this("", "", "", "", "", true, new ArrayList<>());
        }




}
