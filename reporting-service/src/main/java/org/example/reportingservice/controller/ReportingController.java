package org.example.reportingservice.controller;
import lombok.RequiredArgsConstructor;
import org.example.reportingservice.service.ExchangeRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/reporting")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReportingController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("/taux-change")
    public ResponseEntity<Map<String, Object>> getExchangeRates() {
        return ResponseEntity.ok(exchangeRateService.getExchangeRates());
    }

    @GetMapping("/convertir")
    public ResponseEntity<Double> convertCurrency(
            @RequestParam Double montant,
            @RequestParam String deviseSource,
            @RequestParam String deviseCible) {

        Double result = exchangeRateService.convertCurrency(
                montant, deviseSource, deviseCible);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/rapport-conversion")
    public ResponseEntity<Map<String, Object>> getConversionReport(
            @RequestParam Double montant,
            @RequestParam String deviseSource) {

        Map<String, Object> rates = exchangeRateService.getExchangeRates();
        Map<String, Double> ratesMap = (Map<String, Double>) rates.get("rates");

        Map<String, Double> conversions = Map.of(
                "EUR", exchangeRateService.convertCurrency(montant, deviseSource, "EUR"),
                "GBP", exchangeRateService.convertCurrency(montant, deviseSource, "GBP"),
                "JPY", exchangeRateService.convertCurrency(montant, deviseSource, "JPY"),
                "CAD", exchangeRateService.convertCurrency(montant, deviseSource, "CAD")
        );

        return ResponseEntity.ok(Map.of(
                "montantOriginal", montant,
                "deviseSource", deviseSource,
                "conversions", conversions,
                "date", rates.get("date")
        ));
    }
}