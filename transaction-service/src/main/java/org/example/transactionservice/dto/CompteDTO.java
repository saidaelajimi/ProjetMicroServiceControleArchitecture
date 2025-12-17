package org.example.transactionservice.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CompteDTO {
    private Long id;
    private String numeroCompte;
    private String proprietaire;
    private Double solde;
    private LocalDateTime dateCreation;
}