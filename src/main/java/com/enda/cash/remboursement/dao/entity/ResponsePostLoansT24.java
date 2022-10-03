package com.enda.cash.remboursement.dao.entity;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResponsePostLoansT24 {
    @JsonSetter("Cin")
    private String Cin;

    @JsonSetter("Montant")
    private String Montant;

    @JsonSetter("Status")
    private String Status;

    @JsonSetter("Date")
    private String Date;

}
