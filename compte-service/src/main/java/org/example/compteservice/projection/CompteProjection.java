package org.example.compteservice.projection;

import org.example.compteservice.model.Compte;
import org.springframework.data.rest.core.config.Projection;
import java.time.LocalDateTime;

/**
 * Projection pour personnaliser la vue des comptes
 * Accessible via : GET /api/comptes?projection=compteDetails
 */
@Projection(name = "compteDetails", types = { Compte.class })
public interface CompteProjection {
    Long getId();
    String getNumeroCompte();
    String getProprietaire();
    Double getSolde();
    String getTypeCompte();
    LocalDateTime getDateCreation();
}