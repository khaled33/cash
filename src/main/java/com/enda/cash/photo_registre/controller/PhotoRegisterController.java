package com.enda.cash.photo_registre.controller;

import com.enda.cash.photo_registre.dao.entity.PhotoRegister;
import com.enda.cash.photo_registre.service.PhotoRegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/PhotoRegisters")
@Tag(name = "PhotoRegisters Resource", description = "API permettant la gestion des Photo Registers")

public class PhotoRegisterController {
    private PhotoRegisterService photoRegisterService;

    @Operation(summary = "Add Photo Registers")

//    @PostMapping()
//    public ResponseEntity<PhotoRegister> addPhotoRegister(@RequestParam MultipartFile file) {
//        return new ResponseEntity<>( photoRegisterService.addPhotoRegister(file), HttpStatus.CREATED);
//    }
    @PostMapping("")
    public ResponseEntity<PhotoRegister> addPhotoRegister(@RequestBody MultipartFile file) {
        return new ResponseEntity<>(photoRegisterService.addPhotoRegister(file), HttpStatus.CREATED);
    }

    @Operation(summary = "get list Photo Registers by userName")
    @GetMapping("/{userName}")
    public ResponseEntity<List<PhotoRegister>> getPhotoRegisterByAp_Username(@PathVariable String userName) {
        return new ResponseEntity<>(photoRegisterService.getPhotoRegisterByAp_Username(userName), HttpStatus.OK);
    }
}
