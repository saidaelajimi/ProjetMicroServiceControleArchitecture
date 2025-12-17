package org.example.transactionservice.repository;
import org.example.transactionservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCompteSourceNumeroOrCompteDestinationNumero(
            String compteSourceNumero, String compteDestinationNumero);
}