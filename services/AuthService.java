package com.project2025.auth_2.services;

import com.project2025.auth_2.converters.UserConverter;
import com.project2025.auth_2.domain.loginDTO.LoginDTO;
import com.project2025.auth_2.domain.loginDTO.LoginResponseDTO;
import com.project2025.auth_2.domain.loginDTO.RegisterDTO;
import com.project2025.auth_2.domain.user.User;
import com.project2025.auth_2.events.UserRegisteredEvent;
import com.project2025.auth_2.exception.BusinessException;
import com.project2025.auth_2.exception.ErrorCode;
import com.project2025.auth_2.repositories.UserRepository;
import com.project2025.auth_2.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository,
                       UserConverter userConverter,
                       PasswordEncoder passwordEncoder,
                       ApplicationEventPublisher eventPublisher,
                       AuthenticationManager authenticationManager,
                       TokenService tokenService) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    // ===== OPERAÇÕES DE REGISTRO =====

    /**
     * Registra um novo usuário no sistema
     *
     * @param registerDTO dados para registro do usuário
     * @return ID do usuário criado
     */
    @Transactional
    @Operation(description = "Registra um novo usuário no sistema")
    public Long register(RegisterDTO registerDTO) {
        validateUserRegistration(registerDTO);

        User user = createUserFromRegisterDTO(registerDTO);
        user.activate();


        User savedUser = userRepository.save(user);

        publishUserRegisteredEvent(savedUser.getId());

        return savedUser.getId();
    }
     // ===== OPERAÇÕES DE AUTENTICAÇÃO =====

    /**
     * Realiza o login do usuário
     *
     * @param loginDTO dados de login (login e password)
     * @return LoginResponseDTO com token JWT e role do usuário
     */
    @Operation(description = "Realiza o login do usuário")
    public LoginResponseDTO login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password())
            );

            User user = (User) authentication.getPrincipal();
            String token = tokenService.generateToken(user);

            return new LoginResponseDTO(token, user.getRole());
        } catch (AuthenticationException ex) {
            throw new BusinessException("Credenciais inválidas", ErrorCode.INVALID_CREDENTIALS);
        }
    }

    // ===== MÉTODOS AUXILIARES PRIVADOS =====

    /**
     * Valida se um usuário pode ser registrado
     *
     * @param registerDTO dados para registro
     */
    private void validateUserRegistration(RegisterDTO registerDTO) {
        if (userRepository.existsByLogin(registerDTO.login())) {
            throw new BusinessException("Login já cadastrado!", ErrorCode.USER_ALREADY_EXISTS);
        }
    }

    /**
     * Cria uma entidade User a partir do RegisterDTO
     *
     * @param registerDTO dados para registro
     * @return User criado
     */
    private User createUserFromRegisterDTO(RegisterDTO registerDTO) {
        return userConverter.toEntity(registerDTO)
                .withPassword(passwordEncoder.encode(registerDTO.password()))
                .withRole(registerDTO.role());
    }

    /**
     * Publica evento de usuário registrado
     *
     * @param userId ID do usuário registrado
     */
    private void publishUserRegisteredEvent(Long userId) {
        eventPublisher.publishEvent(new UserRegisteredEvent(userId));
    }
}