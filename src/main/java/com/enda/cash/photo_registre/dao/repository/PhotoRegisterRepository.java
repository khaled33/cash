package com.enda.cash.photo_registre.dao.repository;

import com.enda.cash.photo_registre.dao.entity.PhotoRegister;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRegisterRepository extends JpaRepository<PhotoRegister, Long> {

    Optional<List<PhotoRegister>> findAllByAp_Username(String UserName);
}
