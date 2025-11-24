package br.com.fiap.gs.GS.controller;

import br.com.fiap.gs.GS.entities.Curriculo;
import br.com.fiap.gs.GS.entities.Usuario;
import br.com.fiap.gs.GS.service.CurriculoService;
import br.com.fiap.gs.GS.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/curriculo")
public class CurriculoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CurriculoService curriculoService;

    @GetMapping("/editar")
    public String editar(Model model) {

        Usuario usuario = usuarioService.obterUsuarioLogado();

        if (usuario.getCurriculo() == null) {
            usuario.setCurriculo(new Curriculo());
            usuario.getCurriculo().setUsuario(usuario);
        }

        model.addAttribute("curriculo", usuario.getCurriculo());
        return "curriculo/form";
    }

    @PostMapping("/editar")
    public String salvar(@ModelAttribute Curriculo curriculo) {
        Usuario u = usuarioService.obterUsuarioLogado();
        curriculo.setUsuario(u);
        curriculoService.salvar(curriculo);

        return "redirect:/curriculo/ver";
    }

    @GetMapping("/ver")
    public String ver(Model model) {
        Usuario usuario = usuarioService.obterUsuarioLogado();
        model.addAttribute("curriculo", usuario.getCurriculo());
        return "curriculo/ver";
    }
}
