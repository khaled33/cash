package com.enda.cash.agence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenceRepository extends JpaRepository<Agences, Long> {
    Agences findByNomAgence(String name);
}
