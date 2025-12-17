package org.example.transactionservice.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String compteSourceNumero;

    private Long compteSourceId; // Ajouté pour stocker l'ID

    @Column(nullable = false)
    private String compteDestinationNumero;

    private Long compteDestinationId; // Ajouté pour stocker l'ID

    @Column(nullable = false)
    private Double montant;

    @Column(nullable = false)
    private LocalDateTime dateTransaction;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatut statut;

    private String description;

    public enum TransactionStatut {
        SUCCESS, FAILED, PENDING
    }

    @PrePersist
    protected void onCreate() {
        dateTransaction = LocalDateTime.now();
        if (statut == null) {
            statut = TransactionStatut.PENDING;
        }
    }
}