package br.com.fiap.gs.GS.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record UsuarioResponseDto(
        Long id,
        String username,
        String nomeCompleto,
        String email,
        String telefone,
        Boolean enabled,
        List<Long> roles // ou List<RoleResponseDto>
) {}


