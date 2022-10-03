package com.enda.cash.remboursement.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class ResponseQeryConsulPayT24 implements Serializable {
    @JsonSetter("Cin")
    private String Cin;

    @JsonSetter("Montant")
    private String Montant;

    @JsonSetter("Status")
    private int Status;

    @JsonSetter("Date")
    private String Date;

    private String codeAA;

}
