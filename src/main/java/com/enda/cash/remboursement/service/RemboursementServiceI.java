package com.enda.cash.remboursement.service;

import com.enda.cash.remboursement.dao.entity.Remboursement;
import com.enda.cash.remboursement.dao.entity.ResponseApiT24LoanAccountDto;

import java.util.List;

public interface RemboursementServiceI {
    Remboursement creatRemboursement(Remboursement remboursement);

    boolean checkedLastRemboursement(String cinClent);

    List<Remboursement> getRemboursementIdUserAndDate(Long UserId, String Date);

    List<Remboursement> HistoriqueRemboursementIdUser(Long UserId);

    ResponseApiT24LoanAccountDto getListLoansT24(String Cin);

    Remboursement getRemboursementBytransactionIdEdp(String transactionIdEdp);

}
