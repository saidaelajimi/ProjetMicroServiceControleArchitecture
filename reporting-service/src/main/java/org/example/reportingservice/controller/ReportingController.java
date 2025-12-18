package org.example.reportingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.reportingservice.model.ConversionResult;
import org.example.reportingservice.model.ExchangeRateResponse;
import org.example.reportingservice.model.RapportConversion;
import org.example.reportingservice.service.ExchangeRateService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/api/reporting")
@RequiredArgsConstructor

public class ReportingController {

    private final ExchangeRateService exchangeRateService;

    /**
     * GET /api/reporting/taux-change/{devise}
     * Récupère les taux de change pour une devise de base
     */
    @GetMapping(value = "/taux-change/{devise}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ExchangeRateResponse> getExchangeRates(@PathVariable String devise) {
        return exchangeRateService.getExchangeRates(devise);
    }

    /**
     * GET /api/reporting/convertir?montant=100&deviseSource=USD&deviseCible=EUR
     * Convertit un montant d'une devise vers une autre
     */
    @GetMapping(value = "/convertir", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ConversionResult> convertCurrency(
            @RequestParam Double montant,
            @RequestParam String deviseSource,
            @RequestParam String deviseCible) {

        return exchangeRateService.convertCurrency(montant, deviseSource, deviseCible);
    }

    /**
     * GET /api/reporting/rapport-conversion?montant=1000&deviseSource=USD
     * Génère un rapport de conversion vers plusieurs devises
     */
    @GetMapping(value = "/rapport-conversion", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RapportConversion> getRapportConversion(
            @RequestParam Double montant,
            @RequestParam String deviseSource) {

        return exchangeRateService.genererRapportConversion(montant, deviseSource);
    }

    /**
     * GET /api/reporting/comparer?devise1=USD&devise2=EUR
     * Compare les taux entre deux devises
     */
    @GetMapping(value = "/comparer", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, Object>> comparerTaux(
            @RequestParam String devise1,
            @RequestParam String devise2) {

        return exchangeRateService.comparerTaux(devise1, devise2);
    }
}