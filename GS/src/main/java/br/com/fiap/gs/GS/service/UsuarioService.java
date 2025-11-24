package br.com.fiap.gs.GS.service;

import br.com.fiap.gs.GS.dto.UsuarioRequestDto;
import br.com.fiap.gs.GS.dto.UsuarioResponseDto;
import br.com.fiap.gs.GS.dto.UsuarioUpdateRequestDto;
import br.com.fiap.gs.GS.entities.Role;
import br.com.fiap.gs.GS.entities.Usuario;
import br.com.fiap.gs.GS.exception.EmailAlreadyExistsException;
import br.com.fiap.gs.GS.exception.UsernameAlreadyExistsException;
import br.com.fiap.gs.GS.repository.RoleRepository;
import br.com.fiap.gs.GS.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // Listar todos
    public List<UsuarioResponseDto> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // Listar paginado
    public Page<UsuarioResponseDto> listarTodosPaginado(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(this::convertToResponseDto);
    }

    // Buscar por ID
    public UsuarioResponseDto buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return convertToResponseDto(usuario);
    }

    public Usuario buscarUsuarioEntityPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Criar usuário
    @Transactional
    public UsuarioResponseDto criarUsuario(UsuarioRequestDto dto) {
        // Validação de unicidade
        validarEmailEUsername(dto.username(), dto.email(), null);

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setPassword(passwordEncoder.encode(dto.senha()));
        usuario.setEmail(dto.email());
        usuario.setNomeCompleto(dto.nomeCompleto());
        usuario.setTelefone(dto.telefone());
        usuario.setEnabled(dto.enabled() != null ? dto.enabled() : true);

        if (dto.rolesIds() != null && !dto.rolesIds().isEmpty()) {
            usuario.setRoles(buscarRolesPorIds(new HashSet<>(dto.rolesIds())));
        } else {
            Role userRole = roleRepository.findByNome("USER")
                    .orElseThrow(() -> new RuntimeException("Role USER não encontrada"));
            usuario.setRoles(Set.of(userRole));
        }

        Usuario salvo = usuarioRepository.save(usuario);
        return convertToResponseDto(salvo);
    }

    // Atualizar usuário
    @Transactional
    public UsuarioResponseDto atualizarUsuario(Long id, UsuarioUpdateRequestDto dto) {
        Usuario usuario = buscarUsuarioEntityPorId(id);

        usuario.setUsername(dto.username());
        usuario.setEmail(dto.email());
        usuario.setNomeCompleto(dto.nomeCompleto());
        usuario.setTelefone(dto.telefone());
        usuario.setEnabled(dto.enabled());

        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(dto.senha()));
        }

        if (dto.rolesIds() != null) {
            usuario.setRoles(buscarRolesPorIds(new HashSet<>(dto.rolesIds())));
        }

        Usuario salvo = usuarioRepository.save(usuario);
        return convertToResponseDto(salvo);
    }

    // Deletar usuário
    @Transactional
    public void deletarUsuario(Long id) {
        Usuario usuario = buscarUsuarioEntityPorId(id);
        usuarioRepository.delete(usuario);
    }

    // Alternar status (ativo/inativo)
    @Transactional
    public void alternarStatusUsuario(Long id) {
        Usuario usuario = buscarUsuarioEntityPorId(id);
        usuario.setEnabled(!usuario.isEnabled());
        usuarioRepository.save(usuario);
    }

    // Auxiliares
    private Set<Role> buscarRolesPorIds(Set<Long> ids) {
        return ids.stream()
                .map(id -> roleRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Role não encontrada: " + id)))
                .collect(Collectors.toSet());
    }

    private void validarEmailEUsername(String username, String email, Long idExclusao) {
        if (usuarioRepository.existsByUsername(username) &&
                (idExclusao == null || !usuarioRepository.findByUsername(username).get().getId().equals(idExclusao))) {
            throw new RuntimeException("Username já cadastrado");
        }
        if (usuarioRepository.existsByEmail(email) &&
                (idExclusao == null || !usuarioRepository.findByEmail(email).get().getId().equals(idExclusao))) {
            throw new RuntimeException("Email já cadastrado");
        }
    }

    // Converter para DTO
    public UsuarioResponseDto convertToResponseDto(Usuario u) {
        List<Long> rolesIds = u.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toList());

        return new UsuarioResponseDto(
                u.getId(),
                u.getUsername(),
                u.getNomeCompleto(),
                u.getEmail(),
                u.getTelefone(),
                u.isEnabled(),
                rolesIds
        );
    }

    // Obter IDs de roles de um usuário
    public List<Long> obterIdsRolesDoUsuario(Usuario usuario) {
        return usuario.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toList());
    }

    public Usuario obterUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Usuário não logado");
        }
        return (Usuario) auth.getPrincipal();
    }

}
