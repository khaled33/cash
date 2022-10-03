package com.enda.cash.banque;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Banques Resource", description = "API permettant la gestion des Banques")
@AllArgsConstructor
@RestController
@RequestMapping("/banques")
public class BanqueController {
    private BanquesRepository banqueRepository;

    @Operation(summary = "Add Banque")
    @PostMapping("")
    ResponseEntity<Banque> addBanque(@Valid @RequestBody Banque banque) {
        return new ResponseEntity<>(banqueRepository.save(banque), HttpStatus.CREATED);
    }

    @Operation(summary = "Get List Banque")
    @GetMapping("")
    ResponseEntity<BanqueDto> getAllBanque() {

        BanqueDto banque = new BanqueDto();
        banque.setBanques(banqueRepository.getAllBanque());
        banque.setPost(banqueRepository.getPoste());
        return new ResponseEntity<>(banque, HttpStatus.OK);
    }

    @Operation(summary = "Delete Banque By Id")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteBanque(@PathVariable Long id) {
        banqueRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
