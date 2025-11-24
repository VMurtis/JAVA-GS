package br.com.fiap.gs.GS.service;

import br.com.fiap.gs.GS.entities.Curriculo;
import br.com.fiap.gs.GS.entities.Usuario;
import br.com.fiap.gs.GS.repository.CurriculoRepository;
import br.com.fiap.gs.GS.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurriculoService {

    private final CurriculoRepository curriculoRepository;
    private final UsuarioRepository usuarioRepository;


    // 🔹 Listar todos os currículos
    public List<Curriculo> listarTodos() {
        return curriculoRepository.findAll();
    }

    // 🔹 Buscar currículo por ID
    public Curriculo buscarPorId(Long id) {
        return curriculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currículo não encontrado"));
    }

    // 🔹 Buscar currículo pelo usuário
    public Curriculo buscarPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return curriculoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Nenhum currículo encontrado para este usuário"));
    }

    // 🔹 Criar currículo
    public Curriculo criar(Curriculo curriculo, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        curriculo.setUsuario(usuario);
        return curriculoRepository.save(curriculo);
    }

    // 🔹 Atualizar currículo
    public Curriculo atualizar(Long id, Curriculo dados) {
        Curriculo existente = buscarPorId(id);

        existente.setResumoProfissional(dados.getResumoProfissional());
        existente.setTelefone(dados.getTelefone());
        existente.setEndereco(dados.getEndereco());
        existente.setHabilidades(dados.getHabilidades());
        existente.setExperiencia(dados.getExperiencia());
        existente.setFormacao(dados.getFormacao());
        existente.setPortfolio(dados.getPortfolio());

        return curriculoRepository.save(existente);
    }

    // 🔹 Excluir currículo
    public void excluir(Long id) {
        Curriculo existente = buscarPorId(id);
        curriculoRepository.delete(existente);
    }

    public Curriculo salvar(Curriculo curriculo) {
        return curriculoRepository.save(curriculo);
    }

    public Curriculo buscarPorUsuario(Usuario usuario) {
        return curriculoRepository.findByUsuario(usuario)
                .orElse(null);
    }
}
