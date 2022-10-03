package com.enda.cash.versement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResponseHistoriqueVersement {
    private List<DtoHistoriqueVersement> dtoHistoriqueVersements;
    private Double totalVersements;
}
