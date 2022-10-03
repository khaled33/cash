package com.enda.cash.agence;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Agence  Resource", description = "API permettant la gestion des Agence Enda")
@AllArgsConstructor
@RestController
@RequestMapping("/agence")
public class AgenceController {
    private AgenceRepository agenceRepository;

    @Operation(summary = "Add Agence")
    @PostMapping("")
    ResponseEntity<Agences> addBanque(@Valid @RequestBody Agences agence) {
        return new ResponseEntity<>(agenceRepository.save(agence), HttpStatus.CREATED);
    }

    @Operation(summary = "Get List Agence")
    @GetMapping("")
    ResponseEntity<List<Agences>> getAllBanque() {
        return new ResponseEntity<>(agenceRepository.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Delete Agence By Id")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteBanque(@PathVariable Long id) {
        agenceRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
