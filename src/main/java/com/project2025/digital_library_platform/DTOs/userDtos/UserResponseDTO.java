package com.project2025.digital_library_platform.DTOs.userDtos;

import com.project2025.digital_library_platform.entity.user.Role;
import lombok.*;

@Data
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
    private Boolean active;

}
