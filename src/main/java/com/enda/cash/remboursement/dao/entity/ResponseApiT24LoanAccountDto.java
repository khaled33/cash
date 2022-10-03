package com.enda.cash.remboursement.dao.entity;

import lombok.Data;

import java.util.List;

@Data
public class ResponseApiT24LoanAccountDto {
    private List<ResponseApiT24LoanAccount> responseApiT24LoanAccount;
    private boolean cinExiste;
}
