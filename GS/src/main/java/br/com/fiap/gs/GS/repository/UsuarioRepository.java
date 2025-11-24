package br.com.fiap.gs.GS.repository;

import br.com.fiap.gs.GS.dto.UsuarioResponseDto;
import br.com.fiap.gs.GS.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Optional<Usuario> findByUsername(String username);


    Optional<Usuario> findByEmail(String email);


    boolean existsByEmail(String email);


    boolean existsByUsername(String username);


    @Query("""
            SELECT u FROM Usuario u 
            LEFT JOIN FETCH u.roles 
            WHERE u.email = :email
        """)
    Optional<Usuario> findByEmailWithRoles(String email);

    @Query("""
            SELECT u FROM Usuario u 
            LEFT JOIN FETCH u.roles 
            WHERE u.username = :username
        """)
    Optional<Usuario> findByUsernameWithRoles(String username);




    // Contar quantos usuários possuem determinada role
    long countByRoles_Nome(String roleName);

}