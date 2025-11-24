package br.com.fiap.gs.GS.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "TDS_ENTREVISTAS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Entrevista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    private String status; // exemplo: "PENDENTE", "REALIZADA", "CANCELADA"

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "curriculo_id")
    private Curriculo curriculo;
}