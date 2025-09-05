package com.project2025.digital_library_platform.repositories;


import com.project2025.digital_library_platform.entity.user.Role;
import com.project2025.digital_library_platform.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    Optional<User> findByLogin(String login);

    List<User> findByActiveTrue();

    List<User> findByRole(Role role);

    //  * Busca todos os usuários bloqueados
    // List<User> findByBlockedTrue();

}