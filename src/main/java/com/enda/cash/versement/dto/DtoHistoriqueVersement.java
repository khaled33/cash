package com.enda.cash.versement.dto;

import com.enda.cash.versement.dao.entity.StatusVersement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtoHistoriqueVersement {


    private Long id;

    private Double montant;

    private String nmrbulletin;

    private String dateVersement;

    private StatusVersement statutVersement = StatusVersement.En_attente;


    private String transactionId;


}


