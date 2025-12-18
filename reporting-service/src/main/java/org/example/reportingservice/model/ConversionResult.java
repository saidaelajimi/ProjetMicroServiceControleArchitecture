package org.example.reportingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResult {
    private Double montantOriginal;
    private String deviseSource;
    private Double montantConverti;
    private String deviseCible;
    private Double tauxChange;
    private String date;
}