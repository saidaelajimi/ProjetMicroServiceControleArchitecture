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
                // Route pour l'authentification (PUBLIC)
                .route("auth-service", r -> r
                        .path("/auth/**")
                        .uri("http://localhost:8080"))

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

                .build();
    }

    @Bean
    public org.springframework.web.cors.reactive.CorsWebFilter corsWebFilter() {
        org.springframework.web.cors.CorsConfiguration corsConfig = new org.springframework.web.cors.CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOrigin("http://localhost:3000");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");

        org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new org.springframework.web.cors.reactive.CorsWebFilter(source);
    }
}