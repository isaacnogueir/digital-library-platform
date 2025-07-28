package com.project2025.auth_2.controllers;

import com.project2025.auth_2.domain.user.Role;
import com.project2025.auth_2.domain.user.User;
import com.project2025.auth_2.domain.user.UserResponseDTO;
import com.project2025.auth_2.domain.user.UserUpdateDTO;
import com.project2025.auth_2.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Operações relacioandas ao gerencimento de usuários.")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ===== OPERAÇÕES DE PERFIL =====

    @GetMapping("/profile")
    @Operation(summary = "Obter perfil do usuário logado", description = "Retorna as informações do perfil usuário autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil retornado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public ResponseEntity<UserResponseDTO> getProfile(@AuthenticationPrincipal User user) {
        UserResponseDTO profile = userService.getProfile(user);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Atualizar perfil do usuário",
            description = "Atualiza as informações do perfil do usuário autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "409", description = "E-mail ou login já cadastrado")
    })
    public UserResponseDTO updateProfile(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateProfile(id, userUpdateDTO);
    }

    // ===== OPERAÇÕES DE BUSCA =====

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna usuário específico por Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        UserResponseDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/login/{login}")
    @Operation(summary = "Buscar usuário por login",
            description = "Retorna um usuário específico pelo seu login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUserByLogin(@PathVariable String login) {
        UserResponseDTO user = userService.findByLogin(login);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/list")
    @Operation(summary = "Listar todos os usuários",
            description = "Retorna uma lista com todos os usuários cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> list = userService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/active")
    @Operation(summary = "Listar usuários ativos",
            description = "Retorna uma lista com todos os usuários ativos")
    @ApiResponse(responseCode = "200", description = "Lista de usuários ativos retornada com sucesso")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> findActiveUsers() {
        List<UserResponseDTO> users = userService.findActiveUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Buscar usuários por role",
            description = "Retorna uma lista de usuários filtrados por role específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"), @ApiResponse(responseCode = "400", description = "Role inválida")})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getUserByRole(@PathVariable Role role) {
        List<UserResponseDTO> users = userService.findUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    // ===== CONTROLE DE STATUS =====

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Ativar usuário",
            description = "Ativa um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> activateUser(Long id) {
        UserResponseDTO user = userService.activateUser(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Desativar usuário",
            description = "Desativa um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasRole('ADMIN)")
    public ResponseEntity<UserResponseDTO> deactivateUser(Long id) {
        UserResponseDTO user = userService.deactivateUser(id);
        return ResponseEntity.ok(user);
    }
/*
    // ===== VALIDAÇÕES E VERIFICAÇÕES =====

    @Operation(summary = "Verificar se usuário existe ",
            description = "Verifica se um usuário existe pelo ID")
    @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        boolean exists = userService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/email/{email}/exists")
    @Operation(summary = "Verificar se email está em uso",
            description = "Verifica se um email já está cadastrado no sistema")
    @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/login/{login}/exists")
    @Operation(summary = "Verificar se login está em uso",
            description = "Verifica se um login já está cadastrado no sistema")
    @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> existsByLogin(@PathVariable String login) {
        boolean exists = userService.existsByLogin(login);
        return ResponseEntity.ok(exists);

    }
    // ===== PERMISSÕES =====

    @GetMapping("/{id}/can-operate")
    @Operation(summary = "Verificar se usuário pode operar",
            description = "Verifica se um usuário tem permissões para operar no sistema")
    @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> CanUserOperate(@PathVariable Long id) {
        boolean exists = userService.canUserOperate(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/{id}/admin-permissions")
    @Operation(summary = "Verificar permissões administrativas",
            description = "Verifica se um usuário tem permissões administrativas")
    @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> hasAdminPermissions(@PathVariable Long id) {
        boolean hasPermissions = userService.hasAdminPermissions(id);
        return ResponseEntity.ok(hasPermissions);
    }
*/
}