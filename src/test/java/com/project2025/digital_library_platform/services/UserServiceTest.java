package com.project2025.digital_library_platform.services;

import com.project2025.digital_library_platform.converters.UserConverter;
import com.project2025.digital_library_platform.domain.book.Status;
import com.project2025.digital_library_platform.domain.user.Dtos.UserResponseDTO;
import com.project2025.digital_library_platform.domain.user.Dtos.UserUpdateDTO;
import com.project2025.digital_library_platform.domain.user.Role;
import com.project2025.digital_library_platform.domain.user.User;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import com.project2025.digital_library_platform.repositories.UserRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserName testes")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    // =============== DADOS DE ENTRADA ===============

    private User user;
    private UserResponseDTO userResponseDTO;
    private UserUpdateDTO userUpdateDTO;

    @BeforeEach
    void setUp() {

        //User mock
        user = new User();
        user.setId(1L);
        user.setLogin("testador");
        user.setPassword("encodedPassword");
        user.setNome("Mariano Testador");
        user.setEmail("testador@bol.com");
        user.setTelefone("2424852452152");
        user.setEndereco("Tijuca, Rio de Janeiro");
        user.setRole(Role.ADMIN);
        user.activate();

        //Response Mock
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1L);
        userResponseDTO.setLogin("testador");
        userResponseDTO.setNome("Mariano Testador");
        userResponseDTO.setEmail("testador@bol.com");
        userResponseDTO.setEndereco("Tijuca, Rio de janeiro");
        userResponseDTO.setTelefone("2424852452152");
        userResponseDTO.setRole(Role.ADMIN);
        userResponseDTO.setActive(true);

        //Update Mock
        userUpdateDTO = new UserUpdateDTO("Testador de Assis Neto",
                "testador2025",
                "encodedPassword",
                "testador2025@bol.com",
                "Tijuca, Rio de Janeiro",
                "2424852452152");
    }

    private User createTestUser(Long id, String login, String password,
                                String nome, String email, String endereco, String telefone,
                                Role role, boolean active) {
        User testUser = new User();
        testUser.setId(id);
        testUser.setLogin(login);
        testUser.setPassword(password);
        testUser.setNome(nome);
        testUser.setEmail(email);
        testUser.setEndereco(endereco);
        testUser.setTelefone(telefone);
        testUser.setRole(role);
        testUser.setActive(active);

        return testUser;
    }

    private UserResponseDTO createTestUserResponseDTO(Long id, String login, String nome, String email, String endereco, String telefone, Role role, boolean active) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(id);
        dto.setLogin(login);
        dto.setNome(nome);
        dto.setEmail(email);
        dto.setEndereco(endereco);
        dto.setTelefone(telefone);
        dto.setRole(role);
        dto.setActive(active);
        return dto;
    }

    private UserResponseDTO buildUpdatedUserResponse() {
        UserResponseDTO updatedResponse = new UserResponseDTO();
        updatedResponse.setId(1L);
        updatedResponse.setLogin("testador2025");
        updatedResponse.setEmail("testador2025@bol.com");
        updatedResponse.setEndereco("Tijuca, Rio de Janeiro");
        updatedResponse.setTelefone("2424852452152");
        return updatedResponse;
    }

//     // =============== TESTES DE PERFIL ===============

    @Test
    @DisplayName("Deve obter o perfil do usuário com fluxo completo")
    void getProfile_whenValidUser_shouldExecuteCompleteFlow() {

        //ARRANGE
        when(userConverter.toDto(user)).thenReturn(userResponseDTO);

        //ACT
        UserResponseDTO result = userService.getProfile(user);

        //ASSERT
        assertThat(result)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getId()).isEqualTo(1L);
                    assertThat(response.getLogin()).isEqualTo("testador");
                    assertThat(response.getNome()).isEqualTo("Mariano Testador");
                    assertThat(response.getRole()).isEqualTo(Role.ADMIN);
                });

        //VERIFY
        verify(userConverter).toDto(user);
        System.out.println("✅ Obtenção de perfil executada COM SUCESSO!");
    }

    // =============== TESTES DE ATUALIZAÇÃO ===============

    @Test
    @DisplayName("Deve atualizar perfil com sucesso seguindo fluxo completo")
    void updateProfile_WhenValidData_ShouldExecuteCompleteFlow() {

        //ARRANGE
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("testador2025@bol.com")).thenReturn(false);
        when(userRepository.existsByLogin("testador2025")).thenReturn(false);
        doNothing().when(userConverter).updateFromDto(user, userUpdateDTO, passwordEncoder);
        when(userRepository.save(user)).thenReturn(user);
        when(userConverter.toDto(user)).thenReturn(buildUpdatedUserResponse());

        //ACT
        UserResponseDTO result = userService.updateProfile(1L, userUpdateDTO);

        //ASSERT
        assertThat(result)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getId()).isEqualTo(1L);
                    assertThat(response.getLogin()).isEqualTo("testador2025");
                    assertThat(response.getEmail()).isEqualTo("testador2025@bol.com");
                });

        //VERIFY
        InOrder inOrder = inOrder(userRepository, userConverter);
        inOrder.verify(userRepository).findById(1L);
        inOrder.verify(userRepository).existsByEmail("testador2025@bol.com");
        inOrder.verify(userRepository).existsByLogin("testador2025");
        inOrder.verify(userConverter).updateFromDto(user, userUpdateDTO, passwordEncoder);
        inOrder.verify(userRepository).save(user);
        inOrder.verify(userConverter).toDto(user);

        System.out.println("✅ Atualização de perfil executada COM SUCESSO!");

    }

    // =============== TESTES DE BUSCAS ===============

    @Test
    @DisplayName("Deve buscar um usuário por ID")
    void findById_WhenValidid_ShouldReturnUser() {
        //ARRANGE
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userConverter.toDto(user)).thenReturn(userResponseDTO);

        //ACT
        UserResponseDTO result = userService.findById(1L);

        //ASSERT
        assertThat(result)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getId()).isEqualTo(1L);
                    assertThat(response.getLogin()).isEqualTo("testador");
                });
        //VERIFY

        InOrder inOrder = inOrder(userRepository, userConverter);
        inOrder.verify(userRepository).findById(1L);
        inOrder.verify(userConverter).toDto(user);

        System.out.println("✅ Busca por ID executada COM SUCESSO!");

    }

    @ParameterizedTest
    @ValueSource(strings = {"testador", "testuser", "TESTUSER", "TestUser", "test", "user"})
    @DisplayName("Deve retornar todos os usuários")
    void findLogin_WhenValidLogin_ShouldReturnUser(String login) {
        //ARRANGE
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        when(userConverter.toDto(user)).thenReturn(userResponseDTO);

        //ACT
        UserResponseDTO result = userService.findByLogin(login);

        //ASSERT
        assertThat(result)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getId()).isEqualTo(1L);
                    assertThat(response.getLogin()).isEqualTo("testador");
                });

        System.out.println("✅ Busca por login executada COM SUCESSO!");

    }

    @Test
    @DisplayName("Deve listar todos os usuários ")
    void findAll_ShoulderReturnAllUsers() {
// ARRANGE
        List<User> users = Arrays.asList(
                createTestUser(1L, "teste1", "senha1", "Teste Um", "teste1@email.com", "Endereço 1", "111111111", Role.ADMIN, true),
                createTestUser(2L, "teste2", "senha2", "Teste Dois", "teste2@email.com", "Endereço 2", "222222222", Role.USER, true)
        );

        List<UserResponseDTO> expectedDTOs = Arrays.asList(
                createTestUserResponseDTO(1L, "teste1", "Teste Um", "teste1@email.com", "Endereço 1", "111111111", Role.ADMIN, true),
                createTestUserResponseDTO(2L, "teste2", "Teste Dois", "teste2@email.com", "Endereço 2", "222222222", Role.ADMIN, true)
        );

        when(userRepository.findAll()).thenReturn(users);
        when(userConverter.toDto(users.get(0))).thenReturn(expectedDTOs.get(0));
        when(userConverter.toDto(users.get(1))).thenReturn(expectedDTOs.get(1));

        //ACT
        List<UserResponseDTO> result = userService.findAll();

        //ASSERT
        assertThat(result)
                .isNotNull()
                .hasSize(2);

        System.out.println("✅ Listagem de todos os usuários executada COM SUCESSO!");
    }

    @Test
    @DisplayName("Deve retornar usuárior por Role")
    void findUsersByRole_ReturnUserByRole() {
        //ARRANGE
        List<User> adminusers = Arrays.asList(
                createTestUser(1L, "teste1", "senha1", "Teste Um", "teste1@email.com", "Endereço 1", "111111111", Role.ADMIN, true),
                createTestUser(2L, "teste2", "senha2", "Teste Dois", "teste2@email.com", "Endereço 2", "222222222", Role.ADMIN, true)
        );

        List<UserResponseDTO> expectedDTOs = Arrays.asList(
                createTestUserResponseDTO(1L, "teste1", "Teste Um", "teste1@email.com", "Endereço 1", "111111111", Role.ADMIN, true),
                createTestUserResponseDTO(2L, "teste2", "Teste Dois", "teste2@email.com", "Endereço 2", "222222222", Role.ADMIN, true)
        );

        when(userRepository.findByRole(Role.ADMIN)).thenReturn(adminusers);
        when(userConverter.toDto(adminusers.get(0))).thenReturn(expectedDTOs.get(0));
        when(userConverter.toDto(adminusers.get(1))).thenReturn(expectedDTOs.get(1));

        //ACT
        List<UserResponseDTO> result = userService.findUsersByRole(Role.ADMIN);

        //ASERT
        assertThat(result)
                .isNotNull()
                .allMatch(response -> response.getRole().equals(Role.ADMIN));


        System.out.println("✅ Listagem de usuários por role executada COM SUCESSO!");

    }

    @Test
    @DisplayName("Deve retornar apenas usuários ativos")
    void findActiveUsers_ReturnOnlyActive() {
        //ARRANGE
        List<User> activeusers = Arrays.asList(
                createTestUser(1L, "teste1", "senha1", "Teste Um", "teste1@email.com", "Endereço 1", "111111111", Role.ADMIN, true),
                createTestUser(2L, "teste2", "senha2", "Teste Dois", "teste2@email.com", "Endereço 2", "222222222", Role.ADMIN, true)
        );

        List<UserResponseDTO> expectedDTOs = Arrays.asList(
                createTestUserResponseDTO(1L, "teste1", "Teste Um", "teste1@email.com", "Endereço 1", "111111111", Role.ADMIN, true),
                createTestUserResponseDTO(2L, "teste2", "Teste Dois", "teste2@email.com", "Endereço 2", "222222222", Role.ADMIN, true)
        );

        when(userRepository.findByActiveTrue()).thenReturn(activeusers);
        when(userConverter.toDto(activeusers.get(0))).thenReturn(expectedDTOs.get(0));
        when(userConverter.toDto(activeusers.get(1))).thenReturn(expectedDTOs.get(1));

        //ACT
        List<UserResponseDTO> result = userService.findActiveUsers();

        //ASERT
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .allMatch(UserResponseDTO::getActive)
                .allMatch(user -> user.getRole() == Role.ADMIN);

        System.out.println("✅ Listagem de usuários ativos executada COM SUCESSO!");
    }

    @Test
    @DisplayName("Deve ativar usuário com sucesso seguindo fluxo completo")
    void activateUser_WhenValidId_ShouldExecuteCompleteFlow() {
        // ARRANGE
        User inactiveUser = createTestUser(
                1L, "teste1", "34343434", "Teste", "teste@gmail.com",
                "Rua pará", "45454545454", Role.ADMIN, false
        );
        when(userRepository.findById(1L)).thenReturn(Optional.of(inactiveUser));
        when(userRepository.save(any(User.class))).thenReturn(inactiveUser);

        UserResponseDTO activateResponse = createTestUserResponseDTO(
                1L, "teste1", "Teste", "teste@gmail.com",
                "Rua pará", "45454545454", Role.ADMIN, true
        );
        when(userConverter.toDto(any(User.class))).thenReturn(activateResponse);

        // ACT
        UserResponseDTO result = userService.activateUser(1L);

        // ASSERT
        assertTrue(inactiveUser.isActive(), "O usuário deveria estar ativo");
        assertEquals(activateResponse, result);

        verify(userRepository).findById(1L);
        verify(userRepository).save(inactiveUser);
        verify(userConverter).toDto(inactiveUser);

        System.out.println("✅ Ativação de usuário executada COM SUCESSO!");
    }

    @Test
    @DisplayName("Deve desativar usuário com sucesso seguindo fluxo completo")
    void deactivateUser_WhenValidId_ShouldExecuteCompleteFlow() {
        //ARRANGE
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        UserResponseDTO deactiveResponse = createTestUserResponseDTO(1L, "testador", "Mariano Testador", "testador@bol.com", "Tijuca, Rio de Janeiro", "2424852452152", Role.ADMIN, false);
        when(userConverter.toDto(user)).thenReturn(deactiveResponse);

        //ACT
        UserResponseDTO result = userService.deactivateUser(1L);

        //ASSERT
        assertThat(result)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getId()).isEqualTo(1L);
                    assertThat(response.getActive()).isEqualTo(false);
                });

        InOrder inOrder = inOrder(userRepository, userConverter);
        inOrder.verify(userRepository).findById(1L);
        inOrder.verify(userRepository).save(user);
        inOrder.verify(userConverter).toDto(user);

        System.out.println("✅ Desativação de usuário executada COM SUCESSO!");

    }

    // =============== TESTES DE CENÁRIOS DE ERRO ===============

    @Test
    @DisplayName("Deve lançar exceção ao atualizar com email já existente")
    void updateProfile_DuplicateEmail_ShouldThrowBusinessException() {
        // ARRANGE
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("testador2025@bol.com")).thenReturn(true);

        // ACT
        BusinessException ex = assertThrows(BusinessException.class,
                () -> userService.updateProfile(1L, userUpdateDTO));

        // ASSERT
        assertThat(ex).isNotNull()
                .satisfies(exception -> {
                    assertThat(exception.getMessage()).isEqualTo("E-mail já cadastrado!");
                    assertThat(exception.getCode()).isEqualTo(ErrorCode.USER_ALREADY_EXISTS);
                });

        // VERIFY
        verify(userRepository, never()).save(any());
        System.out.println("✅ Validação de email duplicado funcionando CORRETAMENTE!");
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar com login já existente")
    void updateProfile_DuplicateLogin_ShouldThrowBusinessException() {
        // ARRANGE
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("testador2025@bol.com")).thenReturn(false);
        when(userRepository.existsByLogin(anyString())).thenReturn(true);

        // ACT
        BusinessException ex = assertThrows(BusinessException.class,
                () -> userService.updateProfile(1L, userUpdateDTO));

        // ASSERT
        assertThat(ex).isNotNull()
                .satisfies(exception -> {
                    assertThat(exception.getMessage()).isEqualTo("Login já cadastrado!");
                    assertThat(exception.getCode()).isEqualTo(ErrorCode.USER_ALREADY_EXISTS);
                });

        // VERIFY
        verify(userRepository, never()).save(any());
        System.out.println("✅ Validação de login duplicado funcionando CORRETAMENTE!");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar usuário inexistente por ID")
    void findById_NotFound_ShouldThrowBusinessException() {
        // ARRANGE
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // ACT
        BusinessException ex = assertThrows(BusinessException.class,
                () -> userService.findById(999L));

        // ASSERT
        assertThat(ex).isNotNull()
                .satisfies(exception -> {
                    assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado");
                    assertThat(exception.getCode()).isEqualTo(ErrorCode.USER_NOT_FOUND);
                });

        System.out.println("✅ Exceção lançada corretamente para usuário inexistente por ID!");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar usuário inexistente por login")
    void findByLogin_NotFound_ShouldThrowBusinessException() {
        // ARRANGE
        when(userRepository.findByLogin("nonexistent")).thenReturn(Optional.empty());

        // ACT
        BusinessException ex = assertThrows(BusinessException.class,
                () -> userService.findByLogin("nonexistent"));

        // ASSERT
        assertThat(ex).isNotNull()
                .satisfies(exception -> {
                    assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado");
                    assertThat(exception.getCode()).isEqualTo(ErrorCode.USER_NOT_FOUND);
                });

        System.out.println("✅ Exceção lançada corretamente para usuário inexistente por login!");
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar usuário inexistente")
    void updateProfile_NotFound_ShouldThrowBusinessException() {
        // ARRANGE
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // ACT
        BusinessException ex = assertThrows(BusinessException.class,
                () -> userService.updateProfile(999L, userUpdateDTO));

        // ASSERT
        assertThat(ex).isNotNull()
                .satisfies(exception -> {
                    assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado");
                    assertThat(exception.getCode()).isEqualTo(ErrorCode.USER_NOT_FOUND);
                });

        // VERIFY
        verify(userRepository, never()).save(any());
        System.out.println("✅ Exceção lançada corretamente para atualização de usuário inexistente!");
    }

    @Test
    @DisplayName("Deve retornar false quando usuário não encontrado para verificação operacional")
    void canUserOperate_UserNotFound_ShouldReturnFalse() {
        // ARRANGE
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // ACT
        Boolean result = userService.canUserOperate(999L);

        // ASSERT
        assertThat(result).isFalse();
        verify(userRepository).findById(999L);

        System.out.println("✅ Retorno false para usuário inexistente na verificação operacional!");
    }

    @Test
    @DisplayName("Deve retornar false quando usuário não encontrado para verificação administrativa")
    void hasAdminPermissions_UserNotFound_ShouldReturnFalse() {
        // ARRANGE
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // ACT
        boolean result = userService.hasAdminPermissions(999L);

        // ASSERT
        assertThat(result).isFalse();
        verify(userRepository).findById(999L);

        System.out.println("✅ Retorno false para usuário inexistente na verificação administrativa!");
    }
}

