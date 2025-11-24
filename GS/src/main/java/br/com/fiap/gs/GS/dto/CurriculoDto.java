package br.com.fiap.gs.GS.dto;

import jakarta.validation.constraints.NotBlank;

public record CurriculoDto(

        Long id,

        @NotBlank
        String experiencia,

        @NotBlank
        String habilidades,

        @NotBlank
        String escolaridade,

        Long usuarioId

) {}