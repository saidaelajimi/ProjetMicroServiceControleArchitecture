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
    private Long compteSourceId;

    @Column(nullable = false)
    private String compteSourceNumero;

    @Column(nullable = false)
    private Long compteDestinationId;

    @Column(nullable = false)
    private String compteDestinationNumero;

    @Column(nullable = false)
    private Double montant;

    @Column(nullable = false)
    private LocalDateTime dateTransaction;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutTransaction statut;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeTransaction type;

    private String description;

    private String messageErreur;

    @PrePersist
    protected void onCreate() {
        dateTransaction = LocalDateTime.now();
        if (type == null) {
            type = TypeTransaction.VIREMENT;
        }
    }

    public enum StatutTransaction {
        SUCCESS, FAILED, PENDING, ANNULE
    }

    public enum TypeTransaction {
        VIREMENT, DEPOT, RETRAIT
    }
}