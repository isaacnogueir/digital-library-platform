package com.project2025.digital_library_platform.domain.user;

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

/**
 * ENTIDADE QUE REPRESENTA UM USUÁRIO NO SISTEMA.
 * IMPLEMENTA UserDetails PARA INTEGRAÇÃO COM SPRING SECURITY.
 */
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

    /**
     * IDENTIFICADOR ÚNICO DO USUÁRIO (CHAVE PRIMÁRIA).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID ÚNICO DO USUÁRIO")
    private Long id;

    /**
     * LOGIN DO USUÁRIO.
     * DEVE TER ENTRE 3 E 20 CARACTERES E SER ÚNICO.
     */
    @EqualsAndHashCode.Exclude
    @NotBlank(message = "LOGIN É OBRIGATÓRIO.")
    @Size(min = 3, max = 20, message = "LOGIN DEVE ESTAR ENTRE 3 A 20 CARACTERES.")
    @Column(name = "login", unique = true, nullable = false)
    @Schema(description = "LOGIN DO USUÁRIO", example = "mariaclara")
    private String login;

    /**
     * SENHA DO USUÁRIO.
     * CAMPO OBRIGATÓRIO E COMPRIMENTO MÁXIMO DE 100 CARACTERES.
     */
    @With
    @NotBlank(message = "SENHA É OBRIGATÓRIA.")
    @Column(name = "password", nullable = false, length = 100)
    @Schema(description = "SENHA DO USUÁRIO", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    /**
     * NOME COMPLETO DO USUÁRIO.
     * DEVE TER ENTRE 10 E 100 CARACTERES.
     */
    @NotBlank(message = "NOME É OBRIGATÓRIO.")
    @Size(min = 10, max = 100, message = "NOME COMPLETO.")
    @Column(name = "nome", nullable = false)
    @Schema(description = "NOME COMPLETO DO USUÁRIO", example = "MARIA CLARA DINIZ")
    private String nome;

    /**
     * EMAIL DO USUÁRIO.
     * FORMATO VÁLIDO E ÚNICO.
     */
    @Email(message = "FORMATO DE EMAIL INVÁLIDO")
    @NotBlank(message = "EMAIL É OBRIGATÓRIO.")
    @Size(min = 5, max = 100)
    @Column(name = "email", unique = true, nullable = false)
    @Schema(description = "EMAIL DO USUÁRIO", example = "claradiniz@hotmail.com")
    private String email;

    /**
     * TELEFONE DO USUÁRIO.
     * CAMPO OBRIGATÓRIO COM ATÉ 20 CARACTERES.
     */
    @With
    @NotBlank(message = "TELEFONE É OBRIGATÓRIO.")
    @Column(name = "telefone", nullable = false, length = 20)
    @Schema(description = "TELEFONE DO USUÁRIO", example = "55249865")
    private String telefone;

    /**
     * ENDEREÇO DO USUÁRIO.
     * DEVE TER ENTRE 5 E 50 CARACTERES.
     */
    @With
    @NotBlank(message = "ENDEREÇO É OBRIGATÓRIO.")
    @Size(min = 5, max = 50)
    @Column(name = "endereco", nullable = false)
    @Schema(description = "ENDEREÇO DO USUÁRIO", example = "LEBLON, N 78")
    private String endereco;

    /**
     * PAPEL (ROLE) DO USUÁRIO NO SISTEMA (ADMIN, LIBRARIAN, USER, ETC).
     */
    @With
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    @Schema(description = "PAPEL (ROLE) DO USUÁRIO", example = "ADMIN")
    private Role role;

    /**
     * INDICADOR SE O USUÁRIO ESTÁ ATIVO NO SISTEMA.
     * PADRÃO É TRUE.
     */
    @With
    @Column(name = "active", nullable = false)
    @Schema(description = "INDICA SE O USUÁRIO ESTÁ ATIVO", example = "true")
    private boolean active = true;

    /*
     * CAMPOS DE AUDITORIA
     */

    /**
     * DATA E HORA DE CRIAÇÃO DO REGISTRO DO USUÁRIO.
     * NÃO PODE SER ALTERADO MANUALMENTE.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "DATA E HORA DE CRIAÇÃO DO USUÁRIO", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    /**
     * DATA E HORA DA ÚLTIMA ATUALIZAÇÃO DO USUÁRIO.
     */
    @Column(name = "updated_at")
    @Schema(description = "DATA E HORA DA ÚLTIMA ATUALIZAÇÃO", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;


    // METODOS DE CONTROLE DE STATUS DO USUÁRIO


    /**
     * ATIVA O USUÁRIO (SET ACTIVE = TRUE) E ATUALIZA A DATA DE ATUALIZAÇÃO.
     *
     * @return A PRÓPRIA INSTÂNCIA DO USUÁRIO ATUALIZADO
     */
    public User activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    /**
     * DESATIVA O USUÁRIO (SET ACTIVE = FALSE) E ATUALIZA A DATA DE ATUALIZAÇÃO.
     *
     * @return A PRÓPRIA INSTÂNCIA DO USUÁRIO ATUALIZADO
     */
    public User deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
        return this;
    }


    //METODOS AUXILIARES PARA VERIFICAÇÃO DE PERMISSÕES E STATUS


    /**
     * VERIFICA SE O USUÁRIO ESTÁ ATIVO E PODE OPERAR.
     *
     * @return TRUE SE ATIVO, FALSE CASO CONTRÁRIO
     */
    public boolean canOperate() {
        return this.active;
    }

    /**
     * VERIFICA SE O USUÁRIO POSSUI PERMISSÕES DE ADMINISTRADOR OU BIBLIOTECÁRIO.
     *
     * @return TRUE SE FOR ADMIN OU LIBRARIAN, FALSE CASO CONTRÁRIO
     */
    public boolean hasAdmPermissions() {
        return isAdmin() || isLibrarian();
    }

    /**
     * VERIFICA SE O USUÁRIO É ADMINISTRADOR.
     *
     * @return TRUE SE FOR ADMIN, FALSE CASO CONTRÁRIO
     */
    public boolean isAdmin() {
        return Role.ADMIN.equals(this.role);
    }

    /**
     * VERIFICA SE O USUÁRIO É BIBLIOTECÁRIO.
     *
     * @return TRUE SE FOR LIBRARIAN, FALSE CASO CONTRÁRIO
     */
    public boolean isLibrarian() {
        return Role.LIBRARIAN.equals(this.role);
    }


    // METODOS DE AUDITORIA PARA DEFINIR DATAS AO CRIAR OU ATUALIZAR


    /**
     * METODO EXECUTADO ANTES DE PERSISTIR O USUÁRIO NO BANCO.
     * DEFINE AS DATAS DE CRIAÇÃO E ATUALIZAÇÃO.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * METODO EXECUTADO ANTES DE ATUALIZAR O USUÁRIO NO BANCO.
     * ATUALIZA A DATA DE ATUALIZAÇÃO.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // IMPLEMENTAÇÃO DOS MÉTODOS DE UserDetails PARA SPRING SECURITY

    /**
     * RETORNA AS AUTORIDADES (ROLES) DO USUÁRIO PARA O SPRING SECURITY.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * RETORNA O NOME DE USUÁRIO PARA AUTENTICAÇÃO (LOGIN).
     */
    @Override
    public String getUsername() {
        return login;
    }

    /**
     * RETORNA A SENHA PARA AUTENTICAÇÃO.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * INDICA SE A CONTA NÃO ESTÁ EXPIRADA.
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * INDICA SE A CONTA NÃO ESTÁ BLOQUEADA.
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * INDICA SE AS CREDENCIAIS NÃO ESTÃO EXPIRADAS.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * INDICA SE O USUÁRIO ESTÁ HABILITADO.
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
