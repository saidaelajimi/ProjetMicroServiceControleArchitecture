package org.example.transactionservice.service;
import lombok.RequiredArgsConstructor;
import org.example.transactionservice.client.CompteClient;
import org.example.transactionservice.model.Transaction;
import org.example.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CompteClient compteClient;

    @Transactional
    public Transaction effectuerTransfert(Transaction transaction) {
        try {
            // Récupérer les comptes
            var compteSource = compteClient.getCompteByNumero(transaction.getCompteSourceNumero());
            var compteDest = compteClient.getCompteByNumero(transaction.getCompteDestinationNumero());

            // Ajouter les IDs aux champs de la transaction
            transaction.setCompteSourceId(compteSource.getId());
            transaction.setCompteDestinationId(compteDest.getId());

            // Débiter le compte source
            compteClient.debiterCompte(compteSource.getId(), transaction.getMontant());

            // Créditer le compte destination
            compteClient.crediterCompte(compteDest.getId(), transaction.getMontant());

            transaction.setStatut(Transaction.TransactionStatut.SUCCESS);
            transaction.setDescription("Transfert effectué avec succès");

            return transactionRepository.save(transaction);

        } catch (Exception e) {
            transaction.setStatut(Transaction.TransactionStatut.FAILED);
            transaction.setDescription("Échec du transfert: " + e.getMessage());
            return transactionRepository.save(transaction);
        }
    }

    public List<Transaction> getTransactionsByCompte(String numeroCompte) {
        return transactionRepository.findByCompteSourceNumeroOrCompteDestinationNumero(
                numeroCompte, numeroCompte);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction non trouvée"));
    }
}
