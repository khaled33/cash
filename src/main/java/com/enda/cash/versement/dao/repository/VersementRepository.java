package com.enda.cash.versement.dao.repository;

import com.enda.cash.versement.dao.entity.Versement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VersementRepository extends JpaRepository<Versement, Long>, JpaSpecificationExecutor<Versement> {

    @Query(value = "SELECT * FROM versement INNER JOIN user ON versement.ap_id = user.id  WHERE user.id= :idUser", nativeQuery = true)
    List<Versement> findVersementByIdUser(@Param("idUser") Long IdUser);

    @Query(value = "SELECT * FROM versement INNER JOIN user ON versement.ap_id = user.id WHERE user.id=:IdAgent and versement.filter_date_versement =:dateVersement;", nativeQuery = true)
    List<Versement> getVersementByIdAgentAndDateVersement(@Param("IdAgent") Long nomAgence, @Param("dateVersement") String dateVersement);
}
