package org.example.transactionservice.client;
import org.example.transactionservice.dto.CompteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "compte-service", url = "http://localhost:8081")
public interface CompteClient {

    @GetMapping("/api/comptes/{id}")
    CompteDTO getCompteById(@PathVariable Long id);

    @GetMapping("/api/comptes/numero/{numero}")
    CompteDTO getCompteByNumero(@PathVariable String numero);

    @PutMapping("/api/comptes/{id}/debiter")
    CompteDTO debiterCompte(@PathVariable Long id, @RequestParam Double montant);

    @PutMapping("/api/comptes/{id}/crediter")
    CompteDTO crediterCompte(@PathVariable Long id, @RequestParam Double montant);
}