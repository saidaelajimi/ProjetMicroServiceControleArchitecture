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
public class AuthController {

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody LoginRequest request) {
        // Hardcoded users for demo (in production, check database)
        if (validateCredentials(request.getUsername(), request.getPassword())) {
            String role = request.getUsername().equals("admin") ? "ADMIN" : "USER";
            String token = jwtUtil.generateToken(request.getUsername(), role);
            return Mono.just(ResponseEntity.ok(new AuthResponse(token, request.getUsername())));
        }
        
        return Mono.just(ResponseEntity.status(401).build());
    }
    
    private boolean validateCredentials(String username, String password) {
        // Demo users: admin/admin123 and user/user123
        return (username.equals("admin") && password.equals("admin123")) ||
               (username.equals("user") && password.equals("user123"));
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