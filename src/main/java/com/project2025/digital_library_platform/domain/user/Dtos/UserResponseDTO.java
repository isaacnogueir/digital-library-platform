package com.project2025.digital_library_platform.domain.user.Dtos;

import com.project2025.digital_library_platform.domain.user.Role;
import lombok.*;
/**
 * DTO para resposta contendo informações completas de um usuário.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String login;
    private String nome;
    private String email;
    private String endereco;
    private String telefone;
    private Role role;

}
