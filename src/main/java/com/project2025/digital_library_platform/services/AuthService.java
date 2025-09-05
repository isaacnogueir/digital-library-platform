package com.project2025.digital_library_platform.services;

import com.project2025.digital_library_platform.converters.UserConverter;
import com.project2025.digital_library_platform.DTOs.loginDTO.LoginDTO;
import com.project2025.digital_library_platform.DTOs.loginDTO.LoginResponseDTO;
import com.project2025.digital_library_platform.DTOs.loginDTO.RegisterDTO;
import com.project2025.digital_library_platform.entity.user.User;
import com.project2025.digital_library_platform.events.UserRegisteredEvent;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import com.project2025.digital_library_platform.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.transaction.annotation.Transactional;
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


    // OPERAÇÕES DE REGISTRO

        @Transactional
    @Operation(description = "Registra um novo usuário no sistema")
    public Long register(RegisterDTO registerDTO) {
        validateUserRegistration(registerDTO);

        User user = createUserFromRegisterDTO(registerDTO);
        user.activate();


        User savedUser = userRepository.save(user);

        eventPublisher.publishEvent(new UserRegisteredEvent(savedUser.getId()));
        return savedUser.getId();
    }
     // OPERAÇÕES DE AUTENTICAÇÃO

        @Operation(description = "Realiza o login do usuário")
    public LoginResponseDTO login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password())
            );

            org.springframework.security.core.userdetails.User userDetails =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            User user = userRepository.findByLogin(userDetails.getUsername())
                    .orElseThrow(() -> new BusinessException("Usuário não encontrado", ErrorCode.USER_NOT_FOUND));

           String token = tokenService.generateToken(userDetails.getUsername());

           String role = "ROLE_" + user.getRole().name();

            return new LoginResponseDTO(token,role);
        } catch (AuthenticationException ex) {
            throw new BusinessException("Credenciais inválidas", ErrorCode.INVALID_CREDENTIALS);
        }
    }

     //  MÉTODOS AUXILIARES PRIVADOS

        @Operation(description = "Valida se um usuário pode ser registrado")
    private void validateUserRegistration(RegisterDTO registerDTO) {
        if (userRepository.existsByLogin(registerDTO.login())) {
            throw new BusinessException("Login já cadastrado!", ErrorCode.USER_ALREADY_EXISTS);
        }
    }

        @Operation(description = "Cria uma entidade user a partir do DTO")
    private User createUserFromRegisterDTO(RegisterDTO registerDTO) {
        return userConverter.toEntity(registerDTO)
                .withPassword(passwordEncoder.encode(registerDTO.password()))
                .withRole(registerDTO.role());
    }

        @Operation(description = "Publica evento no rabbit quando um usuário é registrado")
    private void publishUserRegisteredEvent(Long userId) {
        eventPublisher.publishEvent(new UserRegisteredEvent(userId));
    }
}