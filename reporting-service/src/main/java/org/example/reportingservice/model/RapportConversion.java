package org.example.reportingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RapportConversion {
    private Double montantOriginal;
    private String deviseSource;
    private Map<String, Double> conversions;
    private String dateRapport;
}