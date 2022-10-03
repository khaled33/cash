package com.enda.cash.UserDetails.Dao.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DemandeUpdatePaswordUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String TelUser;
    @Column(nullable = true)
    private LocalDate DateDomande;
    @Enumerated(EnumType.STRING)
    private StatusDemende StatusDemende;
}
