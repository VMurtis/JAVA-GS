package br.com.fiap.gs.GS.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "TDS_USUARIOS")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;

    private String email;

    private String username;

    private String password;

    private String telefone;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false, name = "ACCOUNT_NON_EXPIRED")
    private boolean accountNonExpired = true;

    @Column(nullable = false, name = "ACCOUNT_NON_LOCKED")
    private boolean accountNonLocked = true;

    @Column(nullable = false, name = "CREDENTIALS_NON_EXPIRED")
    private boolean credentialsNonExpired = true;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Curriculo curriculo;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TDS_USUARIOS_ROLES",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNome()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
