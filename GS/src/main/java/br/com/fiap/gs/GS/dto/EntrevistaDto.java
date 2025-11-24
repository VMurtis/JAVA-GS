package br.com.fiap.gs.GS.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record EntrevistaDto(

        Long id,

        @NotBlank
        String titulo,

        @Future(message = "A data da entrevista deve ser futura")
        LocalDateTime data,

        @NotBlank
        String local,

        Long usuarioId

) {}