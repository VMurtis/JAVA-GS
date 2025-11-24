package br.com.fiap.gs.GS.controller;

import br.com.fiap.gs.GS.dto.UsuarioRequestDto;
import br.com.fiap.gs.GS.dto.UsuarioResponseDto;
import br.com.fiap.gs.GS.entities.Usuario;
import br.com.fiap.gs.GS.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    // ================== LOGIN ==================
    @GetMapping("/login")
    public String loginForm() {
        return "auth/login"; // caminho para o template login.html
    }

    // ================== PÁGINA DO USUÁRIO LOGADO ==================
    @GetMapping("/perfil")
    public String perfilUsuario(Model model, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuarioEntidade = usuarioService.obterUsuarioLogado(); // DTO
            model.addAttribute("usuario", usuarioEntidade);
            return "auth/perfil"; // template perfil.html
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não foi possível carregar o usuário logado.");
            return "redirect:/login";
        }
    }

    // ================== LOGOUT ==================
    @PostMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        // Spring Security já trata logout, podemos redirecionar
        redirectAttributes.addFlashAttribute("sucesso", "Logout realizado com sucesso!");
        return "redirect:/login";
    }

    // ================== EXEMPLO DE USO DA ENTIDADE ==================
    @GetMapping("/perfil/entidade")
    public String perfilUsuarioEntidade(Model model, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioService.obterUsuarioLogado(); // entidade
            if (usuario == null) {
                redirectAttributes.addFlashAttribute("erro", "Usuário não autenticado.");
                return "redirect:/login";
            }

            // Exemplo: pegar roles, nome, email direto da entidade
            model.addAttribute("username", usuario.getUsername());
            model.addAttribute("email", usuario.getEmail());
            model.addAttribute("roles", usuario.getRoles());
            return "auth/perfil-detalhado"; // template detalhado
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao carregar perfil.");
            return "redirect:/login";
        }
    }
}