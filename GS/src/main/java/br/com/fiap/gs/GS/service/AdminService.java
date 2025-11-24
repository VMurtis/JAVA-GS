package br.com.fiap.gs.GS.service;

import br.com.fiap.gs.GS.repository.CurriculoRepository;
import br.com.fiap.gs.GS.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UsuarioRepository usuarioRepository;
    private final CurriculoRepository curriculoRepository;

    public long countUsuarios() { return usuarioRepository.count(); }
    public long countCurriculos() { return curriculoRepository.count(); }

    public long countEntrevistas() { return 0; } // implementar depois
    public long countPendentes() { return 0; } // implementar depois

    /*public Object getUltimosUsuarios() {
        return usuarioRepository.findTop5ByOrderByIdDesc();
    }*/
}