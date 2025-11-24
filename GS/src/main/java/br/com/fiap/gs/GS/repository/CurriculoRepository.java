package br.com.fiap.gs.GS.repository;

import br.com.fiap.gs.GS.entities.Curriculo;
import br.com.fiap.gs.GS.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurriculoRepository extends JpaRepository<Curriculo, Long> {

    @Query("SELECT c FROM Curriculo c " +
            "WHERE (:habilidade IS NULL OR LOWER(c.habilidades) LIKE LOWER(CONCAT('%', :habilidade, '%'))) " +
            "AND (:experiencia IS NULL OR LOWER(c.experiencia) LIKE LOWER(CONCAT('%', :experiencia, '%')))")
    List<Curriculo> buscar(@Param("habilidade") String habilidade,
                           @Param("experiencia") String experiencia);

    Optional<Curriculo> findByUsuario(Usuario usuario);

    boolean existsByUsuario(Usuario usuario);
}

