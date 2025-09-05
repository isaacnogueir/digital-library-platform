package com.project2025.digital_library_platform.services;

import com.project2025.digital_library_platform.converters.UserConverter;
import com.project2025.digital_library_platform.entity.user.*;
import com.project2025.digital_library_platform.DTOs.userDtos.UserResponseDTO;
import com.project2025.digital_library_platform.DTOs.userDtos.UserUpdateDTO;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import com.project2025.digital_library_platform.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.transaction.annotation.Transactional;
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

    //OPERAÇÕES DE PERFIL

        @Operation(description = "Obtém o perfil do usuário logado")
    public UserResponseDTO getProfile(User user) {

        return userConverter.toDto(user);
    }

        @Transactional
    @Operation(description = "Atualiza perfil")
    public UserResponseDTO updateProfile(Long id, UserUpdateDTO userUpdateDTO) {
        User existingUser = findUserById(id);
        validateUserUpdate(existingUser, userUpdateDTO);

        userConverter.updateFromDto(existingUser, userUpdateDTO, passwordEncoder);

        User updatedUser = userRepository.save(existingUser);
        return userConverter.toDto(updatedUser);
    }

    //CONSULTAS

        @Operation(description = "Busca um usuário por ID")
    public UserResponseDTO findById(Long id) {
        User user = findUserById(id);
        return userConverter.toDto(user);
    }

        @Operation(description = "Busca um usuário por login")
    public UserResponseDTO findByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(userConverter::toDto)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado", ErrorCode.USER_NOT_FOUND));
    }

        @Operation(description = "Lista todos os usuários cadastrados")
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userConverter::toDto)
                .toList();
    }

        @Operation(description = "Lista todos os usuários ativos")
    public List<UserResponseDTO> findActiveUsers() {
        return userRepository.findByActiveTrue()
                .stream()
                .filter(User::isActive)
                .map(userConverter::toDto)
                .toList();
    }

        @Operation(description = "Lista usuários por função/role")
    public List<UserResponseDTO> findUsersByRole(Role role) {
        return userRepository.findByRole(role)
                .stream()
                .map(userConverter::toDto)
                .toList();
    }

  //CONTROLE DE STATUS

    @Transactional
    @Operation(description = "Ativa um usuário")
    public UserResponseDTO activateUser(Long id) {
        User user = findUserById(id);
        user.activate();
        User savedUser = userRepository.save(user);
        return userConverter.toDto(savedUser);
    }

    @Transactional
    @Operation(description = "Desativa um usuário")
    public UserResponseDTO deactivateUser(Long id) {
        User user = findUserById(id);
        user.deactivate();
        User savedUser = userRepository.save(user);
        return userConverter.toDto(savedUser);
    }

   // VERIFICAÇÕES E AVALIAÇÕES

    @Operation(description = "Verifica se um usuário existe por ID")
    public boolean existsById(Long id) {

        return userRepository.existsById(id);
    }

        @Operation(description = "Verifica se existe um usuário com o email informado")
    public boolean existsByEmail(String email) {

        return userRepository.existsByEmail(email);
    }

        @Operation(description = "Verifica se existe um usuário com o login informado")
    public boolean existsByLogin(String login) {

        return userRepository.existsByLogin(login);
    }

        @Operation(description = "Verifica se o usuário pode operar no sistema")
    public Boolean canUserOperate(Long id) {
        return userRepository.findById(id)
                .map(User::canOperate)
                .orElse(false);
    }

        @Operation(description = "Verifica se o usuário possui permissões administrativas")
    public boolean hasAdminPermissions(Long id) {
        return userRepository.findById(id)
                .map(User::hasAdmPermissions)
                .orElse(false);
    }

        @Operation(description = "Busca um usuário por ID ou lança exeçao")
    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado", ErrorCode.USER_NOT_FOUND));
    }

        @Operation(description = "Valida se um usuário pode ser atualizado")
    private void validateUserUpdate(User existingUser, UserUpdateDTO userUpdateDTO) {
        
        if (!existingUser.getEmail().equals(userUpdateDTO.email()) && existsByEmail(userUpdateDTO.email())) {
            throw new BusinessException("E-mail já cadastrado!", ErrorCode.USER_ALREADY_EXISTS);
        }

        if (!existingUser.getLogin().equals(userUpdateDTO.login()) && existsByLogin(userUpdateDTO.login())) {
            throw new BusinessException("Login já cadastrado!", ErrorCode.USER_ALREADY_EXISTS);
        }
    }

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