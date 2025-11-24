package br.com.fiap.gs.GS.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TDS_ROLES")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome; // USER ou ADMIN
}
