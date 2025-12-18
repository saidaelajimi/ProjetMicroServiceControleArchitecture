package org.example.transactionservice.client;

import org.example.transactionservice.model.Compte;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * FeignClient pour communiquer avec compte-service (Spring Data REST)
 *
 * IMPORTANT: Les endpoints /search sont générés par Spring Data REST
 * basés sur @RestResource(path = "...", rel = "...")
 */
@FeignClient(
        name = "compte-service",
        // url = "http://localhost:8081", // Décommenter si pas de service discovery
        path = "/api/comptes"
)
public interface CompteClient {

    /**
     * GET /api/comptes/{id}
     * Récupère un compte par son ID
     */
    @GetMapping("/{id}")
    Compte getCompteById(@PathVariable("id") Long id);

    /**
     * GET /api/comptes/search/byNumero?numeroCompte={numero}
     *
     * IMPORTANT: Le path "byNumero" correspond à @RestResource(path = "byNumero")
     * dans CompteRepository.findByNumeroCompte()
     *
     * Note: findByNumeroCompte retourne Optional<Compte>, donc Spring Data REST
     * retourne soit le compte directement, soit une erreur 404 si non trouvé
     */
    @GetMapping("/search/byNumero")
    Compte findCompteByNumero(@RequestParam("numeroCompte") String numeroCompte);

    /**
     * GET /api/comptes/search/byProprietaire?proprietaire={nom}
     * Recherche les comptes par propriétaire
     */
    @GetMapping("/search/byProprietaire")
    Map<String, Object> findComptesByProprietaire(@RequestParam("proprietaire") String proprietaire);

    /**
     * GET /api/comptes/search/byType?typeCompte={type}
     * Recherche les comptes par type
     */
    @GetMapping("/search/byType")
    Map<String, Object> findComptesByType(@RequestParam("typeCompte") String typeCompte);

    /**
     * PATCH /api/comptes/{id}
     * Met à jour partiellement un compte
     *
     * Exemple: Map<String, Object> updates = Map.of("solde", 1500.0);
     */
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Compte updateCompte(
            @PathVariable("id") Long id,
            @RequestBody Compte compte
    );


    /**
     * GET /api/comptes
     * Récupère tous les comptes (avec pagination)
     */
    @GetMapping
    Map<String, Object> getAllComptes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    );

    /**
     * POST /api/comptes
     * Crée un nouveau compte
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Compte createCompte(@RequestBody Compte compte);

    /**
     * DELETE /api/comptes/{id}
     * Supprime un compte
     */
    @DeleteMapping("/{id}")
    void deleteCompte(@PathVariable("id") Long id);
}