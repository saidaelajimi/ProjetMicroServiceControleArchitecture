package org.example.compteservice.service;
import lombok.RequiredArgsConstructor;
import org.example.compteservice.model.Compte;
import org.example.compteservice.repository.CompteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CompteService {

    private final CompteRepository compteRepository;
    private final Random random = new Random();

    public Compte creerCompte(Compte compte) {
        compte.setNumeroCompte(genererNumeroCompte());
        return compteRepository.save(compte);
    }

    public Compte getCompteById(Long id) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));
    }

    public Compte getCompteByNumero(String numeroCompte) {
        return compteRepository.findByNumeroCompte(numeroCompte)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));
    }

    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    @Transactional
    public Compte debiterCompte(Long compteId, Double montant) {
        Compte compte = getCompteById(compteId);
        if (compte.getSolde() < montant) {
            throw new RuntimeException("Solde insuffisant");
        }
        compte.setSolde(compte.getSolde() - montant);
        return compteRepository.save(compte);
    }

    @Transactional
    public Compte crediterCompte(Long compteId, Double montant) {
        Compte compte = getCompteById(compteId);
        compte.setSolde(compte.getSolde() + montant);
        return compteRepository.save(compte);
    }

    private String genererNumeroCompte() {
        return "FR76" + String.format("%011d", random.nextInt(1000000000));
    }
}