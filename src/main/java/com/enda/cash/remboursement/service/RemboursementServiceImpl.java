package com.enda.cash.remboursement.service;

import com.enda.cash.UserDetails.Dao.Entity.RoleName;
import com.enda.cash.UserDetails.Service.AccountService;
import com.enda.cash.UserDetails.Service.AccountServiceImpl;
import com.enda.cash._config.error.ConflictException;
import com.enda.cash._config.error.NotFoundException;
import com.enda.cash._config.error.NumberFormatExceptionApi;
import com.enda.cash.log.LogEvent;
import com.enda.cash.remboursement.dao.entity.*;
import com.enda.cash.remboursement.dao.repository.RemboursementRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class RemboursementServiceImpl implements RemboursementServiceI {

    private final RemboursementRepository remboursementRepository;
    private final RemboursementDataProviderT24 remboursementDataProviderT24;
    private final smsSender sender;

    private final AccountService accountService;
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Remboursement creatRemboursement(Remboursement remboursement) {
        if (!accountService.getCodePin(remboursement.getPinAp())) {
            throw new ConflictException("Pin Incorrecte");
        }
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(remboursement.getDate());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new NumberFormatExceptionApi("la date doit être dans le format yyyy-mm-dd");
        }

        if (  remboursement.getTransactionIdEdp()!=null && remboursementRepository.findByTransactionIdEdp(remboursement.getTransactionIdEdp()).isPresent()) {
            throw new ConflictException("Transaction Id Exist " + remboursement.getTransactionIdEdp());
        }
        remboursement.setTransactionId(UUID.randomUUID().toString());
        ResponsePostLoansT24 responsePostLoansT24 = remboursementDataProviderT24.RestTemplate_PostLoansT24(remboursement.getCodeAA(), remboursement.getMontant(), remboursement.getTransactionId());

        if (responsePostLoansT24 != null) {
            remboursement.setAp(accountService.getcurrentUser());
            remboursement.setDateTransactionRemboursement(LocalDateTime.now());
            remboursement.setDateRemboursement(localDate);
            remboursement.setStatutTransactionT24(responsePostLoansT24.getStatus());
            remboursement.setNextRepayDate(responsePostLoansT24.getDate());
        }

        String msg = "Paiement effectué avec succés le " + LocalDate.now() + " de l'échéance de " + remboursement.getDate() + " qui est d'un montant de " + remboursement.getMontant()
                + " DT. merci pour votre confiance. Numéro vert 80 100 349";

        sender.SendingSMSDMD(remboursement.getTelClient().toString(), msg);


        Remboursement Saveremboursement = remboursementRepository.save(remboursement);
        applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "Créer Remboursement ", accountService.getcurrentUser()));

        return Saveremboursement;
    }

    @Override
    public boolean checkedLastRemboursement(String cinClent) {
        Optional<Long> id = remboursementRepository.findIdLastRemboursementByUserNameAp(AccountService.currentUserName());
        if (id.isPresent()) {
            if (remboursementRepository.findById(id.get()).get().getCinClient().equals(cinClent)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Remboursement> getRemboursementIdUserAndDate(Long UserId, String Date) {

        try {
            LocalDate localDate = LocalDate.parse(Date);
            applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "Consulter les Historique de l'AP ID : " + UserId + "Date de Recherche " + Date, accountService.getcurrentUser()));

            return remboursementRepository.findByAp_IdAndDateRemboursement(UserId, localDate).get();
        } catch (DateTimeParseException e) {

            throw new NumberFormatExceptionApi("la date doit être dans le format yyyy-mm-dd");
        }

    }

    @Override
    public List<Remboursement> HistoriqueRemboursementIdUser(Long UserId) {
        applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "consulter les Historiques  Remboursements de  l'AP ID C: " + UserId, accountService.getcurrentUser()));

        return remboursementRepository.findByAp_Id(UserId).get();
    }

    @Override
    public ResponseApiT24LoanAccountDto getListLoansT24(String cin) {
        int number = 0;
        if (cin.length() != 8) {
            throw new NotFoundException("Cin doit être 8 chiffre ");
        }
        try {
            number = Integer.parseInt(cin);

            List<ResponseQeryConsulPayT24> responseQeryConsulPayT24s = remboursementDataProviderT24.RestTemplate_GetLoansByCin(cin);
            List<ResponseApiT24LoanAccount> listresponseApiT24LoanAccounts = new ArrayList<>();

            for (ResponseQeryConsulPayT24 responseQeryConsulPayT24 : responseQeryConsulPayT24s) {
                ResponseApiT24LoanAccount responseApiT24LoanAccount = new ResponseApiT24LoanAccount();
                responseApiT24LoanAccount.setAAID(responseQeryConsulPayT24.getCodeAA());
                responseApiT24LoanAccount.setNextRepayDate(responseQeryConsulPayT24.getDate());
                responseApiT24LoanAccount.setRepayAmount(responseQeryConsulPayT24.getMontant());
                listresponseApiT24LoanAccounts.add(responseApiT24LoanAccount);
            }
            Boolean cinExiste = false;
            Optional<Long> id = remboursementRepository.findIdLastRemboursementByUserNameAp(AccountService.currentUserName());
            if (id.isPresent()) {
                if (remboursementRepository.findById(id.get()).get().getCinClient().equals(cin)) {
                    cinExiste = true;
                }
            }
            ResponseApiT24LoanAccountDto responseApiT24LoanAccountDto = new ResponseApiT24LoanAccountDto();
            responseApiT24LoanAccountDto.setResponseApiT24LoanAccount(listresponseApiT24LoanAccounts);
            responseApiT24LoanAccountDto.setCinExiste(cinExiste);
            applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "Consulter List Loans T24 de Client par Cin   : " + cin, accountService.getcurrentUser()));

            return responseApiT24LoanAccountDto;

        } catch (NumberFormatException ex) {
            throw new NumberFormatExceptionApi("Cin doit être un nombre");
        }


    }

    @Override
    public Remboursement getRemboursementBytransactionIdEdp(String transactionIdEdp) {
        applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "get Remboursement by transaction Id Edp", accountService.getcurrentUser()));

        return remboursementRepository.findByTransactionIdEdp(transactionIdEdp).orElseThrow(() ->
                new NotFoundException(String.format("No Record with the transaction Id  [%s] was found in our database", transactionIdEdp))
        );
    }


}
