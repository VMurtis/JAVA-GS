package br.com.fiap.gs.GS.config;

import br.com.fiap.gs.GS.entities.Curriculo;
import br.com.fiap.gs.GS.entities.Role;
import br.com.fiap.gs.GS.entities.Usuario;
import br.com.fiap.gs.GS.repository.CurriculoRepository;
import br.com.fiap.gs.GS.repository.RoleRepository;
import br.com.fiap.gs.GS.repository.UsuarioRepository;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final CurriculoRepository curriculoRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {

        // 1️⃣ Criar ROLE ADMIN se não existir
        Role adminRole = roleRepository.findByNome("ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ADMIN")));

        // 2️⃣ Criar ROLE USER se não existir
        Role userRole = roleRepository.findByNome("USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "USER")));

        // 3️⃣ Criar usuário ADMIN se não existir
        Usuario admin = usuarioRepository.findByEmail("admin@admin.com")
                .orElseGet(() -> {
                    Usuario a = new Usuario();
                    a.setNomeCompleto("Administrador do Sistema");
                    a.setUsername("admin");
                    a.setEmail("admin@admin.com");
                    a.setTelefone("11999999999");
                    a.setPassword(encoder.encode("123456"));
                    a.setRoles(Set.of(adminRole));
                    a.setEnabled(true);

                    System.out.println(">>> Usuário ADMIN criado: admin / 123456");
                    return usuarioRepository.save(a);
                });

        // 4️⃣ Criar usuário comum se não existir
        Usuario usuarioNormal = usuarioRepository.findByEmail("usuario@teste.com")
                .orElseGet(() -> {
                    Usuario u = new Usuario();
                    u.setNomeCompleto("Ana Silva");
                    u.setUsername("anasilva");
                    u.setEmail("usuario@teste.com");
                    u.setTelefone("11988887777");
                    u.setPassword(encoder.encode("123456"));
                    u.setRoles(Set.of(userRole));
                    u.setEnabled(true);

                    System.out.println(">>> Usuário comum criado: anasilva / 123456");
                    return usuarioRepository.save(u);
                });

        // 5️⃣ Criar currículo para o usuário comum, se não existir
        if (!curriculoRepository.existsByUsuario(usuarioNormal)) {
            Curriculo c = new Curriculo();
            c.setResumoProfissional("Profissional dedicado com experiência em suporte técnico e atendimento.");
            c.setTelefone("11999999999");
            c.setEndereco("Rua Exemplo, 123 - São Paulo");
            c.setHabilidades("Suporte técnico, atendimento, organização, comunicação");
            c.setExperiencia("2 anos de experiência em suporte ao cliente e manutenção básica.");
            c.setFormacao("Ensino Médio Completo");
            c.setPortfolio("https://meuportfolio.com");
            c.setUsuario(usuarioNormal);

            curriculoRepository.save(c);
            System.out.println(">>> Currículo criado para o usuário 'anasilva'");
        }
    }
}



/*INSERT INTO TDS_ROLES (id, nome, descricao)
VALUES (1, 'ADMIN', 'Administrador do sistema');

INSERT INTO TDS_ROLES (id, nome, descricao)
VALUES (2, 'USER', 'Usuário padrão');

* */