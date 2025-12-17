package org.example.reportingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateService {

    private final RestTemplate restTemplate;

    @Value("${exchange.api.url:https://api.exchangerate-api.com/v4/latest/USD}")
    private String apiUrl;

    public Map<String, Object> getExchangeRates() {
        try {
            log.info(" Appel API: {}", apiUrl);
            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
            log.info(" Réponse API reçue: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Erreur lors de l'appel à l'API de change: {}", e.getMessage(), e);
            // Retourner des taux par défaut en cas d'erreur
            return Map.of(
                    "base", "USD",
                    "date", "2024-01-01",
                    "rates", Map.of(
                            "USD", 1.0,
                            "EUR", 0.85,
                            "GBP", 0.73,
                            "JPY", 110.0,
                            "CAD", 1.25
                    )
            );
        }
    }

    public Double convertCurrency(Double montant, String deviseSource, String deviseCible) {
        log.info("Conversion demandée: {} {} -> {}", montant, deviseSource, deviseCible);

        try {
            Map<String, Object> ratesResponse = getExchangeRates();
            log.info(" Réponse complète: {}", ratesResponse);

            Object ratesObj = ratesResponse.get("rates");
            log.info("Type de 'rates': {}", ratesObj.getClass().getName());
            log.info("Contenu de 'rates': {}", ratesObj);

            Map<String, Object> rates = (Map<String, Object>) ratesObj;
            log.info("Devises disponibles: {}", rates.keySet());

            if (!rates.containsKey(deviseSource)) {
                log.error("Devise source {} non trouvée", deviseSource);
                throw new RuntimeException("Devise source non supportée: " + deviseSource);
            }

            if (!rates.containsKey(deviseCible)) {
                log.error(" Devise cible {} non trouvée", deviseCible);
                throw new RuntimeException("Devise cible non supportée: " + deviseCible);
            }

            // Récupérer les taux (peuvent être Integer ou Double)
            Object tauxSourceObj = rates.get(deviseSource);
            Object tauxCibleObj = rates.get(deviseCible);

            log.info("Taux source {} = {} (type: {})", deviseSource, tauxSourceObj, tauxSourceObj.getClass());
            log.info(" Taux cible {} = {} (type: {})", deviseCible, tauxCibleObj, tauxCibleObj.getClass());

            double tauxSource = convertToDouble(tauxSourceObj);
            double tauxCible = convertToDouble(tauxCibleObj);

            double resultat = montant * (tauxCible / tauxSource);
            log.info("Résultat: {} {} = {} {}", montant, deviseSource, resultat, deviseCible);

            return resultat;

        } catch (Exception e) {
            log.error("Erreur lors de la conversion", e);
            throw new RuntimeException("Erreur de conversion: " + e.getMessage(), e);
        }
    }

    private double convertToDouble(Object value) {
        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else {
            throw new RuntimeException("Type de taux invalide: " + value.getClass());
        }
    }
}