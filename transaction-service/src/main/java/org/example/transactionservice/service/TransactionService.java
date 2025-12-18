package org.example.transactionservice.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transactionservice.client.CompteClient;
import org.example.transactionservice.model.Compte;
import org.example.transactionservice.model.Transaction;
import org.example.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CompteClient compteClient;

    /**
     * Effectue un transfert entre deux comptes
     */
    @Transactional
    public Transaction effectuerTransfert(Transaction transaction) {
        log.info("Début du transfert de {} EUR du compte {} vers {}",
                transaction.getMontant(),
                transaction.getCompteSourceNumero(),
                transaction.getCompteDestinationNumero());

        transaction.setStatut(Transaction.StatutTransaction.PENDING);

        try {
            // 1. Récupérer les comptes via Feign Client
            Compte compteSource = getCompteByNumero(transaction.getCompteSourceNumero());
            Compte compteDest = getCompteByNumero(transaction.getCompteDestinationNumero());

            // 2. Définir tous les IDs requis AVANT la première sauvegarde
            transaction.setCompteSourceId(compteSource.getId());
            transaction.setCompteSourceNumero(compteSource.getNumeroCompte());
            transaction.setCompteDestinationId(compteDest.getId());
            transaction.setCompteDestinationNumero(compteDest.getNumeroCompte());

            // 3. Sauvegarder la transaction avec tous les IDs requis
            transaction = transactionRepository.save(transaction);
            log.info("Transaction créée avec ID: {}", transaction.getId());

            // 4. Vérifier le solde
            if (compteSource.getSolde() < transaction.getMontant()) {
                throw new IllegalStateException("Solde insuffisant dans le compte source");
            }

            // 5. Débiter le compte source
            double nouveauSoldeSource = compteSource.getSolde() - transaction.getMontant();
            compteSource.setSolde(nouveauSoldeSource);
            compteClient.updateCompte(compteSource.getId(), compteSource);

            log.info("Compte source débité: {} (nouveau solde: {})",
                    compteSource.getNumeroCompte(), nouveauSoldeSource);

            // 6. Créditer le compte destination
            double nouveauSoldeDest = compteDest.getSolde() + transaction.getMontant();
            compteDest.setSolde(nouveauSoldeDest);
            compteClient.updateCompte(compteDest.getId(), compteDest);

            log.info("Compte destination crédité: {} (nouveau solde: {})",
                    compteDest.getNumeroCompte(), nouveauSoldeDest);

            // 7. Marquer la transaction comme réussie
            transaction.setStatut(Transaction.StatutTransaction.SUCCESS);
            transaction.setDescription("Transfert effectué avec succès");

        } catch (FeignException.NotFound e) {
            log.error("Compte non trouvé: {}", e.getMessage());
            transaction.setStatut(Transaction.StatutTransaction.FAILED);
            transaction.setMessageErreur("Compte non trouvé");
            transaction.setDescription("Échec: Compte inexistant");

            // Ne pas sauvegarder si les IDs requis ne sont pas définis
            if (transaction.getCompteSourceId() == null || transaction.getCompteDestinationId() == null) {
                throw new RuntimeException("Impossible de créer la transaction: compte(s) inexistant(s)", e);
            }

        } catch (IllegalStateException e) {
            log.error("Erreur de validation: {}", e.getMessage());
            transaction.setStatut(Transaction.StatutTransaction.FAILED);
            transaction.setMessageErreur(e.getMessage());
            transaction.setDescription("Échec: " + e.getMessage());

        } catch (FeignException e) {
            log.error("Erreur de communication avec compte-service: {}", e.getMessage());
            transaction.setStatut(Transaction.StatutTransaction.FAILED);
            transaction.setMessageErreur("Erreur de communication: " + e.getMessage());
            transaction.setDescription("Échec: Erreur de communication avec le service de comptes");

        } catch (Exception e) {
            log.error("Erreur inattendue lors du transfert: {}", e.getMessage(), e);
            transaction.setStatut(Transaction.StatutTransaction.FAILED);
            transaction.setMessageErreur("Erreur système: " + e.getMessage());
            transaction.setDescription("Échec du transfert");
        }

        // Sauvegarder à nouveau pour mettre à jour le statut final
        // Sauvegarder à nouveau pour mettre à jour le statut final
        // UNIQUEMENT si les IDs requis sont présents
        if (transaction.getCompteSourceId() != null && transaction.getCompteDestinationId() != null) {
            return transactionRepository.save(transaction);
        }
        return transaction;
    }

    /**
     * Récupère un compte par son numéro via FeignClient
     *
     * SIMPLIFIÉ: Maintenant findCompteByNumero retourne directement un Compte
     * car findByNumeroCompte retourne Optional<Compte> dans le repository
     */
    private Compte getCompteByNumero(String numeroCompte) {
        return compteClient.findCompteByNumero(numeroCompte);
    }

    /**
     * Récupère toutes les transactions d'un compte
     */
    public List<Transaction> getTransactionsByCompte(String numeroCompte) {
        return transactionRepository.findByCompteSourceNumeroOrCompteDestinationNumero(
                numeroCompte, numeroCompte);
    }

    /**
     * Récupère toutes les transactions
     */
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    /**
     * Récupère une transaction par ID
     */
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction non trouvée avec l'ID: " + id));
    }

    /**
     * Annule une transaction (si possible)
     * Crée une transaction inverse pour annuler l'opération
     */
    @Transactional
    public Transaction annulerTransaction(Long transactionId) {
        Transaction transaction = getTransactionById(transactionId);

        if (transaction.getStatut() != Transaction.StatutTransaction.SUCCESS) {
            throw new IllegalStateException("Seules les transactions réussies peuvent être annulées");
        }

        if (transaction.getStatut() == Transaction.StatutTransaction.ANNULE) {
            throw new IllegalStateException("Cette transaction est déjà annulée");
        }

        log.info("Annulation de la transaction #{}", transactionId);

        // Créer une transaction inverse
        Transaction annulation = new Transaction();
        annulation.setCompteSourceId(transaction.getCompteDestinationId());
        annulation.setCompteSourceNumero(transaction.getCompteDestinationNumero());
        annulation.setCompteDestinationId(transaction.getCompteSourceId());
        annulation.setCompteDestinationNumero(transaction.getCompteSourceNumero());
        annulation.setMontant(transaction.getMontant());
        annulation.setType(Transaction.TypeTransaction.VIREMENT);
        annulation.setDescription("Annulation de la transaction #" + transactionId);

        Transaction result = effectuerTransfert(annulation);

        // Si l'annulation a réussi, marquer la transaction originale comme annulée
        if (result.getStatut() == Transaction.StatutTransaction.SUCCESS) {
            transaction.setStatut(Transaction.StatutTransaction.ANNULE);
            transaction.setDescription(transaction.getDescription() + " [ANNULÉE]");
            transactionRepository.save(transaction);
            log.info("Transaction #{} annulée avec succès", transactionId);
        }

        return result;
    }
}