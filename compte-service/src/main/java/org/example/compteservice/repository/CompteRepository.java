package org.example.compteservice.repository;
import org.example.compteservice.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

/**
 * Spring Data REST génère automatiquement les endpoints suivants :
 *
 * GET    /api/comptes           -> Liste tous les comptes
 * GET    /api/comptes/{id}      -> Récupère un compte par ID
 * POST   /api/comptes           -> Crée un nouveau compte
 * PUT    /api/comptes/{id}      -> Met à jour un compte
 * PATCH  /api/comptes/{id}      -> Met à jour partiellement un compte
 * DELETE /api/comptes/{id}      -> Supprime un compte
 * GET    /api/comptes/search    -> Endpoints de recherche personnalisés
 */
@RepositoryRestResource(path = "comptes", collectionResourceRel = "comptes")

public interface CompteRepository extends JpaRepository<Compte, Long> {

    // Recherche par numéro de compte
    // Accessible via : GET /api/comptes/search/findByNumeroCompte?numeroCompte=FR76...
    @RestResource(path = "byNumero", rel = "findByNumeroCompte")
    Optional<Compte> findByNumeroCompte(String numeroCompte);

    // Recherche par propriétaire
    // Accessible via : GET /api/comptes/search/findByProprietaire?proprietaire=John
    @RestResource(path = "byProprietaire", rel = "findByProprietaire")
    Iterable<Compte> findByProprietaire(String proprietaire);

    // Recherche par type de compte
    // Accessible via : GET /api/comptes/search/findByTypeCompte?typeCompte=COURANT
    @RestResource(path = "byType", rel = "findByTypeCompte")
    Iterable<Compte> findByTypeCompte(String typeCompte);
}