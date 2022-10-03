package com.enda.cash.versement.service;

import com.enda.cash.versement.dao.entity.StatusVersement;
import com.enda.cash.versement.dao.entity.Versement;
import com.enda.cash.versement.dto.ResponseHistoriqueVersement;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface VersementServiceI {


    Versement addVersement(String nmrbulletin, Double montant, LocalDateTime dateRemboursement, int CodePin, Long IdBanque, MultipartFile file);

    public List<Versement> getVersementByNomAgenceAndDateVersement(Long idUser, String date);

    public List<Versement> filterVersementsAp(Long idUser);

    ResponseHistoriqueVersement getHistoriqueVersementsAp();

    Versement updateStatutVersement(Long idVersement, StatusVersement versement);

}
