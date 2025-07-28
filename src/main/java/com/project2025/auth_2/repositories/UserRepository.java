package com.project2025.auth_2.repositories;

import com.project2025.auth_2.domain.user.Role;
import com.project2025.auth_2.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // ===== VERIFICAÇÕES DE EXISTÊNCIA =====

    /**
     * Verifica se existe um usuário com o login informado
     * @param login login do usuário para verificação
     */
    boolean existsByLogin(String login);

    /**
     * Verifica se existe um usuário com o email informado
     * @param email email do usuário para verificação
     */
    boolean existsByEmail(String email);

    // ===== BUSCAS POR IDENTIFICADOR =====

    /**
     * Busca um usuário pelo login
     * @param login login do usuário
     */
    Optional<User> findByLogin(String login);


    // ===== BUSCAS POR STATUS E PROPRIEDADES =====

    /**
     * Busca todos os usuários ativos
     */
    List<User> findByActiveTrue();

    /**
     * Busca usuários por role/função
     * @param role função do usuário (ADMIN, USER, etc.)
     */
    List<User> findByRole(Role role);

    // ===== MÉTODOS COMENTADOS =====

    // /**
    //  * Busca todos os usuários bloqueados
    //  */
    // List<User> findByBlockedTrue();

}