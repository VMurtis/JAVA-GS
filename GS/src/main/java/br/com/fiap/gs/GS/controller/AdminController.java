package br.com.fiap.gs.GS.controller;

import br.com.fiap.gs.GS.dto.UsuarioRequestDto;
import br.com.fiap.gs.GS.dto.UsuarioResponseDto;
import br.com.fiap.gs.GS.dto.UsuarioUpdateRequestDto;
import br.com.fiap.gs.GS.entities.Usuario;
import br.com.fiap.gs.GS.exception.EmailAlreadyExistsException;
import br.com.fiap.gs.GS.exception.UsernameAlreadyExistsException;
import br.com.fiap.gs.GS.repository.RoleRepository;
import br.com.fiap.gs.GS.service.AdminService;
import br.com.fiap.gs.GS.service.CurriculoService;
import br.com.fiap.gs.GS.service.EntrevistaService;
import br.com.fiap.gs.GS.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UsuarioService usuarioService;
    private final RoleRepository roleRepository;
    private final AdminService adminService;
    private final CurriculoService curriculoService;
    private final EntrevistaService entrevistaService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsuarios", adminService.countUsuarios());
        model.addAttribute("totalCurriculos", adminService.countCurriculos());
        model.addAttribute("totalEntrevistas", adminService.countEntrevistas());
        model.addAttribute("pendentes", adminService.countPendentes());
        return "admin/dashboard";
    }

    // LISTAR usuários
    /*@GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "admin/usuarios/listar";
    }*/


    // No AdminController - Método CORRETO para o Requisito
    @GetMapping("/usuarios")
    public String listarUsuarios(
            @PageableDefault(size = 10, sort = "nomeCompleto") Pageable pageable, // <-- Injeta Pageable
            Model model) {

        Page<UsuarioResponseDto> paginaUsuarios = usuarioService.listarTodosPaginado(pageable);

        model.addAttribute("pagina", paginaUsuarios); // Objeto Page
        model.addAttribute("usuarios", paginaUsuarios.getContent()); // Conteúdo da página

        return "admin/usuarios/listar";
    }


    // CADASTRAR USUÁRIO
    @GetMapping("/usuarios/novo")
    public String novoUsuarioForm(Model model) {
        model.addAttribute("usuario", new UsuarioRequestDto());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/usuarios/novo";
    }

    @PostMapping("/usuarios")
    public String criarUsuario(
            @Valid @ModelAttribute("usuario") UsuarioRequestDto usuarioDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        // 1. Erros de DTO/Validação (@NotBlank, @Email, etc.)
        if (result.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "admin/usuarios/novo";
        }

        try {
            // Tenta criar o usuário
            usuarioService.criarUsuario(usuarioDto);
            redirectAttributes.addFlashAttribute("sucesso", "Usuário criado com sucesso!");
            return "redirect:/admin/usuarios";

            // 2. Erro de Negócio: Username Duplicado
        } catch (UsernameAlreadyExistsException e) {
            // Adiciona a mensagem de erro no campo 'username' do formulário
            result.rejectValue("username", "error.usuario.username", e.getMessage());
            model.addAttribute("roles", roleRepository.findAll());
            return "admin/usuarios/novo";

            // 3. Erro de Negócio: Email Duplicado
        } catch (EmailAlreadyExistsException e) {
            // Adiciona a mensagem de erro no campo 'email' do formulário
            result.rejectValue("email", "error.usuario.email", e.getMessage());
            model.addAttribute("roles", roleRepository.findAll());
            return "admin/usuarios/novo";

            // 4. Erro Genérico (Ex: Role não encontrada, erro de BD desconhecido)
        } catch (Exception e) {
            model.addAttribute("erro", "Erro interno ao criar usuário: " + e.getMessage());
            model.addAttribute("roles", roleRepository.findAll());
            return "admin/usuarios/novo";
        }
    }



    // VISUALIZAR USUÁRIO
    @GetMapping("/usuarios/{id}")
    public String visualizarUsuario(@PathVariable Long id, Model model) {
        UsuarioResponseDto usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        return "admin/usuarios/visualizar";
    }

    // EDITAR USUÁRIO
    @GetMapping("/usuarios/{id}/editar")
    public String editarUsuarioForm(@PathVariable Long id, Model model) {
        Usuario usuarioEntity = usuarioService.buscarUsuarioEntityPorId(id);
        List<Long> rolesIds = usuarioService.obterIdsRolesDoUsuario(usuarioEntity);

        UsuarioUpdateRequestDto usuarioDto = new UsuarioUpdateRequestDto(
                usuarioEntity.getUsername(),
                usuarioEntity.getEmail(),
                usuarioEntity.getNomeCompleto(),
                usuarioEntity.getTelefone(),
                "", // senha em branco
                Set.copyOf(rolesIds),
                usuarioEntity.isEnabled()
        );

        model.addAttribute("usuario", usuarioDto);
        model.addAttribute("usuarioId", id);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("usuarioRolesIds", rolesIds);
        return "admin/usuarios/edita";
    }

    @PutMapping("/usuarios/{id}")
    public String atualizarUsuario(
            @PathVariable Long id,
            @Valid @ModelAttribute("usuario") UsuarioUpdateRequestDto usuarioDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        List<Long> rolesIds = new ArrayList<>(usuarioDto.rolesIds());

        if (result.hasErrors()) {
            model.addAttribute("usuarioId", id);
            model.addAttribute("roles", roleRepository.findAll());
            UsuarioResponseDto usuario = usuarioService.buscarPorId(id);
            model.addAttribute("usuarioRolesIds", rolesIds);
            return "admin/usuarios/edita";
        }

        try {
            usuarioService.atualizarUsuario(id, usuarioDto);
            redirectAttributes.addFlashAttribute("sucesso", "Usuário atualizado com sucesso!");
            return "redirect:/admin/usuarios";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao atualizar usuário: " + e.getMessage());
            model.addAttribute("usuarioId", id);
            model.addAttribute("roles", roleRepository.findAll());
            UsuarioResponseDto usuario = usuarioService.buscarPorId(id);
            model.addAttribute("usuarioRolesIds", rolesIds);
            return "admin/usuarios/edita";
        }
    }


    // DELETAR USUÁRIO
    @DeleteMapping("/usuarios/{id}")
    public String deletarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.deletarUsuario(id);
            redirectAttributes.addFlashAttribute("sucesso", "Usuário deletado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar usuário: " + e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }
}