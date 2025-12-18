package org.example.reportingservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportingservice.model.ConversionResult;
import org.example.reportingservice.model.ExchangeRateResponse;
import org.example.reportingservice.model.RapportConversion;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateService {

    private final WebClient webClient;

    /**
     * Récupère les taux de change depuis l'API externe via WebClient
     */
    public Mono<ExchangeRateResponse> getExchangeRates(String baseCurrency) {
        return webClient.get()
                .uri("/v6/latest/" + baseCurrency)
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class)
                .doOnSuccess(response -> log.info("Taux de change récupérés pour: {}", baseCurrency))
                .doOnError(error -> log.error("Erreur lors de la récupération des taux: {}", error.getMessage()))
                .onErrorResume(error -> {
                    // En cas d'erreur, retourner des taux par défaut
                    log.warn("Utilisation des taux de change par défaut");
                    return Mono.just(getDefaultRates(baseCurrency));
                });
    }

    /**
     * Convertit un montant d'une devise vers une autre
     */
    public Mono<ConversionResult> convertCurrency(Double montant, String deviseSource, String deviseCible) {
        return getExchangeRates(deviseSource)
                .map(response -> {
                    Map<String, Double> rates = response.getConversion_rates();

                    Double tauxCible = rates.get(deviseCible.toUpperCase());
                    if (tauxCible == null) {
                        throw new RuntimeException("Devise non supportée: " + deviseCible);
                    }

                    Double montantConverti = montant * tauxCible;

                    return new ConversionResult(
                            montant,
                            deviseSource.toUpperCase(),
                            montantConverti,
                            deviseCible.toUpperCase(),
                            tauxCible,
                            LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                    );
                });
    }

    /**
     * Génère un rapport de conversion vers plusieurs devises
     */
    public Mono<RapportConversion> genererRapportConversion(Double montant, String deviseSource) {
        return getExchangeRates(deviseSource)
                .map(response -> {
                    Map<String, Double> rates = response.getConversion_rates();
                    Map<String, Double> conversions = new HashMap<>();

                    // Devises principales pour le rapport
                    String[] devisesImportantes = {"USD", "EUR", "GBP", "JPY", "CAD", "CHF", "AUD", "CNY"};

                    for (String devise : devisesImportantes) {
                        if (!devise.equals(deviseSource.toUpperCase()) && rates.containsKey(devise)) {
                            conversions.put(devise, montant * rates.get(devise));
                        }
                    }

                    return new RapportConversion(
                            montant,
                            deviseSource.toUpperCase(),
                            conversions,
                            LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                    );
                });
    }

    /**
     * Compare les taux de change entre deux devises
     */
    public Mono<Map<String, Object>> comparerTaux(String devise1, String devise2) {
        Mono<ExchangeRateResponse> rates1 = getExchangeRates(devise1);
        Mono<ExchangeRateResponse> rates2 = getExchangeRates(devise2);

        return Mono.zip(rates1, rates2)
                .map(tuple -> {
                    ExchangeRateResponse response1 = tuple.getT1();
                    ExchangeRateResponse response2 = tuple.getT2();

                    Map<String, Object> comparaison = new HashMap<>();
                    comparaison.put("devise1", devise1.toUpperCase());
                    comparaison.put("devise2", devise2.toUpperCase());
                    comparaison.put("taux_" + devise1 + "_vers_" + devise2,
                            response1.getConversion_rates().get(devise2.toUpperCase()));
                    comparaison.put("taux_" + devise2 + "_vers_" + devise1,
                            response2.getConversion_rates().get(devise1.toUpperCase()));
                    comparaison.put("date", response1.getTime_last_update_utc());

                    return comparaison;
                });
    }

    /**
     * Retourne les taux par défaut en cas d'échec de l'API
     */
    private ExchangeRateResponse getDefaultRates(String baseCurrency) {
        ExchangeRateResponse defaultResponse = new ExchangeRateResponse();
        defaultResponse.setResult("success");
        defaultResponse.setBase_code(baseCurrency);
        defaultResponse.setTime_last_update_utc(LocalDateTime.now().toString());

        Map<String, Double> defaultRates = new HashMap<>();
        defaultRates.put("USD", 1.0);
        defaultRates.put("EUR", 0.92);
        defaultRates.put("GBP", 0.79);
        defaultRates.put("JPY", 149.50);
        defaultRates.put("CAD", 1.36);
        defaultRates.put("CHF", 0.88);
        defaultRates.put("AUD", 1.53);
        defaultRates.put("CNY", 7.24);

        defaultResponse.setConversion_rates(defaultRates);

        return defaultResponse;
    }
}