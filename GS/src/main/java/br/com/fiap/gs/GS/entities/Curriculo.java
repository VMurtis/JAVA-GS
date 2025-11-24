package br.com.fiap.gs.GS.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "TDS_CURRICULOS")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Curriculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;



    private String resumoProfissional;
    private String telefone;
    private String endereco;

    @Column(length = 400)
    private String habilidades;

    @Column(length = 800)
    private String experiencia;

    @Column(length = 800)
    private String formacao;

    private String portfolio;


}
