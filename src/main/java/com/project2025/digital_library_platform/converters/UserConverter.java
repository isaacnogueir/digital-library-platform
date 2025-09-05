package com.project2025.digital_library_platform.converters;

import com.project2025.digital_library_platform.DTOs.loginDTO.RegisterDTO;
import com.project2025.digital_library_platform.entity.user.User;
import com.project2025.digital_library_platform.DTOs.userDtos.UserResponseDTO;
import com.project2025.digital_library_platform.DTOs.userDtos.UserUpdateDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final PasswordEncoder passwordEncoder;

    public UserConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO toDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getLogin(),
                user.getNome(),
                user.getEmail(),
                user.getEndereco(),
                user.getTelefone(),
                user.getRole(),
                user.isActive());
    }

    public User toEntity(RegisterDTO dto) {
        return User.builder()
                .login(dto.login())
                .password(passwordEncoder.encode(dto.password()))
                .nome(dto.nome())
                .email(dto.email())
                .endereco(dto.endereco())
                .telefone(dto.telefone())
                .role(dto.role())
                .build();
    }

    public void updateFromDto(User user, UserUpdateDTO dto, PasswordEncoder encoder) {
        user.setLogin(dto.login());
        user.setPassword(encoder.encode(dto.password()));
        user.setNome(dto.nome());
        user.setEmail(dto.email());
        user.setEndereco(dto.endereco());
        user.setTelefone(dto.telefone());
    }
}
