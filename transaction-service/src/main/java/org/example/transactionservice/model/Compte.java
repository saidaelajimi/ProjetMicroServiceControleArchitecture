package org.example.transactionservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO pour représenter un Compte depuis compte-service
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compte {
    private Long id;
    private String numeroCompte;
    private String proprietaire;
    private Double solde;
    private String typeCompte;
    private LocalDateTime dateCreation;
}
