package com.project2025.digital_library_platform.services;

import com.project2025.digital_library_platform.converters.UserConverter;
import com.project2025.digital_library_platform.domain.user.*;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import com.project2025.digital_library_platform.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserConverter userConverter, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }

    // ===== OPERAÇÕES DE PERFIL =====

    /**
     * Obtém o perfil do usuário logado
     *
     * @param user usuário logado
     * @return UserResponseDTO com dados do perfil
     */
    @Operation(description = "Obtém o perfil do usuário logado")
    public UserResponseDTO getProfile(User user) {
        return userConverter.toDto(user);
    }

    /**
     * Atualiza o perfil do usuário logado
     *
     * @param user usuário logado
     * @return UserResponseDTO com dados atualizados
     */
    @Transactional
    @Operation(description = "Atualiza o perfil do usuário logado")
    public UserResponseDTO updateProfile(Long id, UserUpdateDTO userUpdateDTO) {
        User existingUser = findUserById(id);
        validateUserUpdate(existingUser, userUpdateDTO);

        userConverter.updateFromDto(existingUser, userUpdateDTO, passwordEncoder);

        User updatedUser = userRepository.save(existingUser);
        return userConverter.toDto(updatedUser);
    }

    // ===== OPERAÇÕES DE CONSULTA =====

    /**
     * Busca um usuário por ID
     *
     * @param id identificador do usuário
     * @return UserResponseDTO com dados do usuário
     */
    @Operation(description = "Busca um usuário por ID")
    public UserResponseDTO findById(Long id) {
        User user = findUserById(id);
        return userConverter.toDto(user);
    }

    /**
     * Busca um usuário por login
     *
     * @param login login do usuário
     * @return UserResponseDTO com dados do usuário
     */
    @Operation(description = "Busca um usuário por login")
    public UserResponseDTO findByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(userConverter::toDto)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado", ErrorCode.USER_NOT_FOUND));
    }

    /**
     * Lista todos os usuários cadastrados
     *
     * @return lista de UserResponseDTO
     */
    @Operation(description = "Lista todos os usuários cadastrados")
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userConverter::toDto)
                .toList();
    }

    /**
     * Lista todos os usuários ativos
     *
     * @return lista de UserResponseDTO ativos
     */
    @Operation(description = "Lista todos os usuários ativos")
    public List<UserResponseDTO> findActiveUsers() {
        return userRepository.findByActiveTrue()
                .stream()
                .map(userConverter::toDto)
                .toList();
    }

    /**
     * Lista usuários por função/role
     *
     * @param role função do usuário
     * @return lista de UserResponseDTO com a função especificada
     */
    @Operation(description = "Lista usuários por função/role")
    public List<UserResponseDTO> findUsersByRole(Role role) {
        return userRepository.findByRole(role)
                .stream()
                .map(userConverter::toDto)
                .toList();
    }

    // ===== CONTROLE DE STATUS =====

    /**
     * Ativa um usuário
     *
     * @param id identificador do usuário
     * @return UserResponseDTO com dados do usuário ativado
     */
    @Transactional
    @Operation(description = "Ativa um usuário")
    public UserResponseDTO activateUser(Long id) {
        User user = findUserById(id);
        user.activate();
        User savedUser = userRepository.save(user);
        return userConverter.toDto(savedUser);
    }

    /**
     * Desativa um usuário
     *
     * @param id identificador do usuário
     * @return UserResponseDTO com dados do usuário desativado
     */
    @Transactional
    @Operation(description = "Desativa um usuário")
    public UserResponseDTO deactivateUser(Long id) {
        User user = findUserById(id);
        user.deactivate();
        User savedUser = userRepository.save(user);
        return userConverter.toDto(savedUser);
    }

    // ===== VERIFICAÇÕES E VALIDAÇÕES =====

    /**
     * Verifica se um usuário existe por ID
     *
     * @param id identificador do usuário
     * @return true se existe, false caso contrário
     */
    @Operation(description = "Verifica se um usuário existe por ID")
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    /**
     * Verifica se existe um usuário com o email informado
     *
     * @param email email para verificação
     * @return true se existe, false caso contrário
     */
    @Operation(description = "Verifica se existe um usuário com o email informado")
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Verifica se existe um usuário com o login informado
     *
     * @param login login para verificação
     * @return true se existe, false caso contrário
     */
    @Operation(description = "Verifica se existe um usuário com o login informado")
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    /**
     * Verifica se o usuário pode operar no sistema
     *
     * @param id identificador do usuário
     * @return true se pode operar, false caso contrário
     */
    @Operation(description = "Verifica se o usuário pode operar no sistema")
    public Boolean canUserOperate(Long id) {
        return userRepository.findById(id)
                .map(User::canOperate)
                .orElse(false);
    }

    /**
     * Verifica se o usuário possui permissões administrativas
     *
     * @param id identificador do usuário
     * @return true se possui permissões admin, false caso contrário
     */
    @Operation(description = "Verifica se o usuário possui permissões administrativas")
    public boolean hasAdminPermissions(Long id) {
        return userRepository.findById(id)
                .map(User::hasAdmPermissions)
                .orElse(false);
    }

    // ===== MÉTODOS AUXILIARES PRIVADOS =====

    /**
     * Busca um usuário por ID ou lança exceção se não encontrado
     *
     * @param id identificador do usuário
     * @return User encontrado
     */
    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado", ErrorCode.USER_NOT_FOUND));
    }

    /**
     * Valida se um usuário pode ser atualizado (email e login únicos)
     *
     * @param existingUser  usuário existente
     * @param userUpdateDTO dados para atualização
     */
    private void validateUserUpdate(User existingUser, UserUpdateDTO userUpdateDTO) {
        // Valida email apenas se foi alterado
        if (!existingUser.getEmail().equals(userUpdateDTO.email()) && existsByEmail(userUpdateDTO.email())) {
            throw new BusinessException("E-mail já cadastrado!", ErrorCode.USER_ALREADY_EXISTS);
        }

        // Valida login apenas se foi alterado
        if (!existingUser.getLogin().equals(userUpdateDTO.login()) && existsByLogin(userUpdateDTO.login())) {
            throw new BusinessException("Login já cadastrado!", ErrorCode.USER_ALREADY_EXISTS);
        }
    }

    // ===== MÉTODOS COMENTADOS =====

    /*
    /**
     * Lista todos os usuários bloqueados
     * @return lista de UserResponseDTO bloqueados
     */
    /*
    @Operation(description = "Lista todos os usuários bloqueados")
    public List<UserResponseDTO> getBlockedUsers() {
        return userRepository.findByBlockedTrue()
                .stream()
                .map(userConverter::toDto)
                .toList();
    }
    */
}