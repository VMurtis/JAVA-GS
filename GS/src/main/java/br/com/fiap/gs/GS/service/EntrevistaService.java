package br.com.fiap.gs.GS.service;

import br.com.fiap.gs.GS.entities.Entrevista;
import br.com.fiap.gs.GS.repository.EntrevistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntrevistaService {

    private final EntrevistaRepository entrevistaRepository;

    public List<Entrevista> listarTodas() {
        return entrevistaRepository.findAll();
    }

    public Entrevista salvar(Entrevista entrevista) {
        return entrevistaRepository.save(entrevista);
    }

    public void deletar(Long id) {
        entrevistaRepository.deleteById(id);
    }
}
