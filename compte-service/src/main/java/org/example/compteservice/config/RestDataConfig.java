package org.example.compteservice.config;


import org.example.compteservice.model.Compte;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestDataConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config,
            CorsRegistry cors) {

        // Exposer les IDs dans les réponses JSON
        config.exposeIdsFor(Compte.class);



        // Configuration du chemin de base
        config.setBasePath("/api");
    }
}