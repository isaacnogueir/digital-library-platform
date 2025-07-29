package com.project2025.digital_library_platform.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.project2025.digital_library_platform.domain.user.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Segredo para assinar/verificar o token
    private static final String secret = "meu-segredo";

    // Gera um token com login do usuário e validade de 2 horas
    public String generateToken(User user) {
        return JWT.create()
                .withIssuer("auth-api") // Identificador da aplicação que gerou o token
                .withSubject(user.getLogin()) // Quem é o "dono" do token (login)
                .withExpiresAt(generateExpirationDate()) // Data de expiração
                .sign(Algorithm.HMAC256(secret)); // Algoritmo de assinatura
    }

    // Valida o token e retorna o login (subject) contido nele
    public String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }


    // Gera a data de expiração para 2 horas no futuro
    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00")); // UTC-3 (Brasil)
    }
}
