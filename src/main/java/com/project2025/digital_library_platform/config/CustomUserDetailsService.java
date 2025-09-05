package com.project2025.digital_library_platform.config;


import com.project2025.digital_library_platform.entity.user.User;
import com.project2025.digital_library_platform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Metodo usado pelo AuthenticationManager para autenticar o usuário no login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado!"+ username));

    return org.springframework.security.core.userdetails.User.builder()
            .username(user.getLogin())
            .password(user.getPassword())
            .authorities("ROLE_USER")
            .build();

       /* return userRepository.findByLogin(username).orElseThrow(
                () -> new BusinessException("Usuário não encontrado", ErrorCode.USER_NOT_FOUND)); */
    }
}