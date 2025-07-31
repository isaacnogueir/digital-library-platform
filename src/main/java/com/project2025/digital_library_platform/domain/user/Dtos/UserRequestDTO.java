package com.project2025.digital_library_platform.domain.user.Dtos;

import com.project2025.digital_library_platform.domain.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para requisição de usuário.
 * Contém os dados necessários para criar um novo usuário.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Login é necessário")
    private String login;

    @NotBlank(message = "Nome é necessário")
    private String nome;

    @NotBlank(message = "Senha é necessária")
    private String password;

    @NotBlank(message = "Email é necessário")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Endereço é necessário")
    private String endereco;

    @NotBlank(message = "Endereço é necessário")
    private String telefone;

    private Role role;

}
