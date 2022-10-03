package com.enda.cash.banque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BanquesRepository extends JpaRepository<Banque, Long> {
    @Query(value = "SELECT * FROM banque WHERE banque.bank_name !='Poste' ", nativeQuery = true)
    List<Banque> getAllBanque();

    @Query(value = "SELECT * FROM banque WHERE banque.bank_name ='Poste' ", nativeQuery = true)
    Banque getPoste();

}
