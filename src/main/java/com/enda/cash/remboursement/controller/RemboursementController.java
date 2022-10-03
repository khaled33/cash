package com.enda.cash.remboursement.controller;

import com.enda.cash.remboursement.dao.entity.Remboursement;
import com.enda.cash.remboursement.dao.entity.ResponseApiT24LoanAccountDto;
import com.enda.cash.remboursement.service.RemboursementServiceI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/remboursements")
@Validated
@Tag(name = "Remboursements Resource", description = "API permettant la gestion des Remboursements")
public class RemboursementController {
    private final RemboursementServiceI remboursementServiceI;


    @Operation(summary = "Get list Loans T24", description = "Get list Loans T24")
    @GetMapping("/getLoansByCin/{Cin}")
    public ResponseEntity<ResponseApiT24LoanAccountDto> getListLoansT24(@PathVariable String Cin) {

        return new ResponseEntity<>(remboursementServiceI.getListLoansT24(Cin), HttpStatus.OK);
    }

    @Operation(summary = "Returns true if the customer cin equals the last refund cin inserted by the ap")
    @GetMapping("/checkedLastRemboursement/{Cin}")
    public ResponseEntity<Boolean> checkedLastRemboursement(@PathVariable String Cin) {

        return new ResponseEntity<>(remboursementServiceI.checkedLastRemboursement(Cin), HttpStatus.OK);
    }

    @Operation(summary = "Save Remboursement")
    @PostMapping("")
    public ResponseEntity<Remboursement> checkedLastRemboursement(@Valid @RequestBody Remboursement remboursement) {

        return new ResponseEntity<>(remboursementServiceI.creatRemboursement(remboursement), HttpStatus.CREATED);
    }


    @Operation(summary = "Filter Remboursement by IdUser And Date Remboursement (back office) ")
    @GetMapping("/{UserId}/{date}")
    public ResponseEntity<List<Remboursement>> getRemboursementIdUserAndDate(@PathVariable Long UserId, @PathVariable String date) {
        return new ResponseEntity<>(remboursementServiceI.getRemboursementIdUserAndDate(UserId, date), HttpStatus.OK);
    }

    @Operation(summary = "Historique Remboursement by Id User")
    @GetMapping("/{UserId}")
    public ResponseEntity<List<Remboursement>> HistoriqueRemboursementIdUser(@PathVariable Long UserId) {
        return new ResponseEntity<>(remboursementServiceI.HistoriqueRemboursementIdUser(UserId), HttpStatus.OK);

    }

    @Operation(summary = "Get Remboursement by Transaction Id Edp")
    @GetMapping("/edp/{transactionIdEdp}")
    public Remboursement getRemboursementBytransactionIdEdp(@PathVariable String transactionIdEdp) {
        return remboursementServiceI.getRemboursementBytransactionIdEdp(transactionIdEdp);
    }
}
