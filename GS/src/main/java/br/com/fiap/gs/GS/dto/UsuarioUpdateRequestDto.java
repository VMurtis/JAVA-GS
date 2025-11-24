package br.com.fiap.gs.GS.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;



import java.util.Set;

public record UsuarioUpdateRequestDto(
        @NotBlank(message = "O nome completo é obrigatório")
        String nomeCompleto,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "O username é obrigatório")
        String username,

        // SENHA É OPCIONAL NO UPDATE: Não coloque @NotBlank
        String senha, // Se for null ou vazio, não será alterada


        String telefone,

        // Lista de IDs das Roles
        Set<Long> rolesIds,

        Boolean enabled
) {}