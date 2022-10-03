package com.enda.cash.remboursement.dao.entity;

import lombok.Data;

@Data
public class ResponseApiT24LoanAccount {
    private String AAID;
    private String NextRepayDate;
    private String RepayAmount;
}
