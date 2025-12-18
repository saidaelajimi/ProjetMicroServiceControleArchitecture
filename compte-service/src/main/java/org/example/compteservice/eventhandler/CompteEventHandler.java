package org.example.compteservice.eventhandler;

import org.example.compteservice.model.Compte;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Gestion des événements du cycle de vie des entités Compte
 */
@Component
@RepositoryEventHandler(Compte.class)
public class CompteEventHandler {

    @HandleBeforeCreate
    public void handleCompteBeforeCreate(Compte compte) {
        // Validation avant création
        if (compte.getSolde() == null) {
            compte.setSolde(0.0);
        }
        System.out.println("Création d'un nouveau compte pour : " + compte.getProprietaire());
    }

    @HandleAfterCreate
    public void handleCompteAfterCreate(Compte compte) {
        System.out.println("Compte créé avec succès : " + compte.getNumeroCompte());
    }

    @HandleBeforeSave
    public void handleCompteBeforeSave(Compte compte) {
        System.out.println("Mise à jour du compte : " + compte.getNumeroCompte());
    }

    @HandleBeforeDelete
    public void handleCompteBeforeDelete(Compte compte) {
        System.out.println("Suppression du compte : " + compte.getNumeroCompte());
    }
}
