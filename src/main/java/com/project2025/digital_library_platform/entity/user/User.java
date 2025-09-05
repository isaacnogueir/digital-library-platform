package com.project2025.digital_library_platform.entity.user;

import io.swagger.v3.oas.annotations.media.Schema;
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
import java.util.List;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_role", columnList = "role"),
        @Index(name = "idx_user_active", columnList = "active")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID ÚNICO DO USUÁRIO")
    private Long id;

        @EqualsAndHashCode.Exclude
    @NotBlank(message = "LOGIN É OBRIGATÓRIO.")
    @Size(min = 3, max = 20, message = "LOGIN DEVE ESTAR ENTRE 3 A 20 CARACTERES.")
    @Column(name = "login", unique = true, nullable = false)
    @Schema(description = "LOGIN DO USUÁRIO", example = "mariaclara")
    private String login;

        @With
    @NotBlank(message = "SENHA É OBRIGATÓRIA.")
    @Column(name = "password", nullable = false, length = 100)
    @Schema(description = "SENHA DO USUÁRIO", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

        @NotBlank(message = "NOME É OBRIGATÓRIO.")
    @Size(min = 10, max = 100, message = "NOME COMPLETO.")
    @Column(name = "nome", nullable = false)
    @Schema(description = "NOME COMPLETO DO USUÁRIO", example = "MARIA CLARA DINIZ")
    private String nome;

        @Email(message = "FORMATO DE EMAIL INVÁLIDO")
    @NotBlank(message = "EMAIL É OBRIGATÓRIO.")
    @Size(min = 5, max = 100)
    @Column(name = "email", unique = true, nullable = false)
    @Schema(description = "EMAIL DO USUÁRIO", example = "claradiniz@hotmail.com")
    private String email;

        @With
    @NotBlank(message = "TELEFONE É OBRIGATÓRIO.")
    @Column(name = "telefone", nullable = false, length = 20)
    @Schema(description = "TELEFONE DO USUÁRIO", example = "55249865")
    private String telefone;

        @With
    @NotBlank(message = "ENDEREÇO É OBRIGATÓRIO.")
    @Size(min = 5, max = 100)
    @Column(name = "endereco", nullable = false)
    @Schema(description = "ENDEREÇO DO USUÁRIO", example = "LEBLON, N 78")
    private String endereco;

        @With
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    @Schema(description = "PAPEL (ROLE) DO USUÁRIO", example = "ADMIN")
    private Role role;

        @With
    @Column(name = "active", nullable = false)
    @Schema(description = "INDICA SE O USUÁRIO ESTÁ ATIVO", example = "true")
    private boolean active = true;

        @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "DATA E HORA DE CRIAÇÃO DO USUÁRIO", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

        @Column(name = "updated_at")
    @Schema(description = "DATA E HORA DA ÚLTIMA ATUALIZAÇÃO", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

        @Schema(description = "Ativa usuário e atualiza data de atualizacao")
    public User activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

        @Schema(description = "Desaita usuario e atualiza data de atualizacao")
    public User deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
        return this;
    }


    //METODOS AUXILIARES PARA VERIFICAÇÃO DE PERMISSÕES E STATUS


        @Schema(description = "Verifica se usuário oestá ativo")
    public boolean canOperate() {
        return this.active;
    }

        @Schema(description = "Verifica se o usuário tem permissão de admiistrador ou bibliotecario")
    public boolean hasAdmPermissions() {
        return isAdmin() || isLibrarian();
    }

        @Schema(description = "Verifica se usuário é admin")
    public boolean isAdmin() {
        return Role.ADMIN.equals(this.role);
    }

        @Schema(description = "Verifica se é bibliotecário")
    public boolean isLibrarian() {
        return Role.LIBRARIAN.equals(this.role);
    }


    //METODOS DE AUDITORIA PARA DEFINIR DATAS AO CRIAR OU ATUALIZAR


        @PrePersist
    @Schema(description = "Define as datas de criaçao e atualização e é executado antes de persistir usuário banco")
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

        @PreUpdate
    @Schema(description = "Atuaza a data de atualização - metodo executado antes de ataulizar o usuario no banco")
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

     //IMPLEMENTAÇÃO DOS MÉTODOS DE UserDetails PARA SPRING SECURITY

        @Override
    @Schema(description = "Retorna roles do usuário para o security")
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

        @Override
    @Schema(description = "Retorna usuario para login")
    public String getUsername() {
        return login;
    }

        @Override
    @Schema(description = "Retorna senha para autenticação")
    public String getPassword() {
        return password;
    }

        @Override
    @Schema(description = "Indica se não está expirada")
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

        @Override
    @Schema(description = "Indica se a conta não está bloqueado")
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

        @Override
    @Schema(description = "Indica se credencias não venceram")
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

        @Override
    @Schema(description = "Indica se usuário está habilitado")
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
