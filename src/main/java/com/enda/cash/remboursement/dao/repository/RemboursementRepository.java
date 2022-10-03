package com.enda.cash.remboursement.dao.repository;

import com.enda.cash.remboursement.dao.entity.Remboursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {
    @Query(value = "SELECT  max(r.id)  FROM remboursement r INNER JOIN   user u ON r.ap_id=u.id WHERE u.username=:username ", nativeQuery = true)
    Optional<Long> findIdLastRemboursementByUserNameAp(@Param("username") String username);

    Optional<List<Remboursement>> findByAp_Id(Long idUser);

    Optional<Remboursement> findByTransactionIdEdp(String TransactionIdEdp);

    Optional<List<Remboursement>> findByAp_IdAndDateRemboursement(Long idUser, LocalDate Date);


}
