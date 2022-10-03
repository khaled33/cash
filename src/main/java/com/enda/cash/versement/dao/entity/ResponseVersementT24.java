package com.enda.cash.versement.dao.entity;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseVersementT24 {
    @JsonSetter("bnkAccount")
    private String bnkAccount;

    @JsonSetter("Amount")
    private Double Amount;

    @JsonSetter("VoucherNo")
    private String VoucherNo;

    @JsonSetter("Status")
    private String Status;

    @JsonSetter("Date")
    private String Date;
}
