package org.example.apigateway.config;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route pour compte-service
                .route("compte-service", r -> r
                        .path("/api/comptes/**")
                        .uri("lb://COMPTE-SERVICE"))

                // Route pour transaction-service
                .route("transaction-service", r -> r
                        .path("/api/transactions/**")
                        .uri("lb://TRANSACTION-SERVICE"))

                // Route pour reporting-service
                .route("reporting-service", r -> r
                        .path("/api/reporting/**")
                        .uri("lb://REPORTING-SERVICE"))

                // Route pour l'authentification
                .route("auth-service", r -> r
                        .path("/auth/**")
                        .filters(f -> f
                                .rewritePath("/auth/(?<segment>.*)", "/${segment}")
                        )
                        .uri("http://localhost:8080")) // Le gateway gère l'auth

                .build();
    }
}