package org.example.compteservice.controller;
import lombok.RequiredArgsConstructor;
import org.example.compteservice.model.Compte;
import org.example.compteservice.service.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comptes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CompteController {

    private final CompteService compteService;

    @PostMapping
    public ResponseEntity<Compte> creerCompte(@RequestBody Compte compte) {
        return new ResponseEntity<>(compteService.creerCompte(compte), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        return ResponseEntity.ok(compteService.getCompteById(id));
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<Compte> getCompteByNumero(@PathVariable String numero) {
        return ResponseEntity.ok(compteService.getCompteByNumero(numero));
    }

    @GetMapping
    public ResponseEntity<List<Compte>> getAllComptes() {
        return ResponseEntity.ok(compteService.getAllComptes());
    }

    @PutMapping("/{id}/debiter")
    public ResponseEntity<Compte> debiterCompte(@PathVariable Long id, @RequestParam Double montant) {
        return ResponseEntity.ok(compteService.debiterCompte(id, montant));
    }

    @PutMapping("/{id}/crediter")
    public ResponseEntity<Compte> crediterCompte(@PathVariable Long id, @RequestParam Double montant) {
        return ResponseEntity.ok(compteService.crediterCompte(id, montant));
    }
}