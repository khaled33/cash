package com.enda.cash.versement.controller;

import com.enda.cash.versement.dao.entity.StatusVersement;
import com.enda.cash.versement.dao.entity.Versement;
import com.enda.cash.versement.dto.ResponseHistoriqueVersement;
import com.enda.cash.versement.service.VersementServiceI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Tag(name = "Versements Resource", description = "API permettant la gestion des versements")

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/versements")
public class VersementController {
    private VersementServiceI versementServiceI;

    @Operation(summary = "Ajoute un versements")
    @PostMapping
    public ResponseEntity<Versement> addVersement(@RequestParam(required = true) @NotEmpty(message = "Numero du bulletin vide") String nmrbulletin,
                                                  @RequestParam(required = true)
                                                  @Min(value = 0, message = "Montant doit être Positive")

                                                          Double montant,
                                                  @RequestParam
                                                  @Min(value = 10000, message = "Le code pin doit être 5 chiffre")
                                                  @Max(value = 99999, message = "Le code pin doit être 5 chiffre") int CodePin,
                                                  @RequestParam Long IdBanque,
                                                  @RequestBody @NotNull(message = "File vide") MultipartFile file) {

        return new ResponseEntity<>(versementServiceI.addVersement(nmrbulletin, montant, LocalDateTime.now(), CodePin, IdBanque, file), HttpStatus.CREATED);

    }

    @Operation(summary = "Historique des Versements par AP")
    @GetMapping("")
    ResponseEntity<ResponseHistoriqueVersement> historiqueVersement() {

        return new ResponseEntity<>(versementServiceI.getHistoriqueVersementsAp(), HttpStatus.OK);
    }


    @Operation(summary = "filtre les Versements par AP et date Versement  (Back Office)")
    @GetMapping("/{idUser}/{dateVersement}")
    ResponseEntity<List<Versement>> getVersementByUserAndDateVersement(@PathVariable(name = "idUser") Long idUser, @PathVariable(name = "dateVersement") String date) {

        return new ResponseEntity<>(versementServiceI.getVersementByNomAgenceAndDateVersement(idUser, date), HttpStatus.OK);
    }

    @Operation(summary = "filtre les Versements par id AP  (Back Office)")
    @GetMapping("/ap/{idUser}")
    ResponseEntity<List<Versement>> filterVersementsAp(@PathVariable(name = "idUser") Long idUser) {

        return new ResponseEntity<>(versementServiceI.filterVersementsAp(idUser), HttpStatus.OK);
    }

    @Operation(summary = "Update Statut Versements")
    @PutMapping("/{idVersement}/{statusVersement}")
    ResponseEntity<Versement> updateStatutVersement(@PathVariable(name = "idVersement") Long idVersement, @PathVariable(name = "statusVersement") StatusVersement statusVersement) {

        return new ResponseEntity<>(versementServiceI.updateStatutVersement(idVersement, statusVersement), HttpStatus.CREATED);
    }


}
