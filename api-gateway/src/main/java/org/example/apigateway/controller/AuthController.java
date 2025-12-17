package org.example.apigateway.controller;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.example.apigateway.security.JwtUtil;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody LoginRequest request) {
        // Ici, normalement on vérifierait dans une base de données
        // Pour la démo, on accepte tout login

        String token = jwtUtil.generateToken(request.getUsername(), "USER");

        return Mono.just(ResponseEntity.ok(new AuthResponse(token, request.getUsername())));
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<AuthResponse>> register(@RequestBody LoginRequest request) {
        // Logique d'enregistrement simplifiée
        String token = jwtUtil.generateToken(request.getUsername(), "USER");

        return Mono.just(ResponseEntity.ok(new AuthResponse(token, request.getUsername())));
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    @RequiredArgsConstructor
    public static class AuthResponse {
        private final String token;
        private final String username;
        private final String type = "Bearer";
    }
}