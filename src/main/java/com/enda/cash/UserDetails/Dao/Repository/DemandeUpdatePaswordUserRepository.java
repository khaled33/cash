package com.enda.cash.UserDetails.Dao.Repository;

import com.enda.cash.UserDetails.Dao.Entity.DemandeUpdatePaswordUser;
import com.enda.cash.UserDetails.Dao.Entity.StatusDemende;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface DemandeUpdatePaswordUserRepository extends JpaRepository<DemandeUpdatePaswordUser, Long> {

    @Query(value = "SELECT * From demande_update_pasword_user WHERE status_demende=:statusDemende", nativeQuery = true)
    List<DemandeUpdatePaswordUser> findAByStatusDemende(String statusDemende);

    @Query(value = "SELECT * From demande_update_pasword_user WHERE user_name=:UserName AND status_demende=:statusDemende", nativeQuery = true)
    Optional<DemandeUpdatePaswordUser> findByUserNameAndStatusDemende(String UserName, String statusDemende);
}
