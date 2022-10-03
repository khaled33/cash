package com.enda.cash.versement.dao.entity;

import com.enda.cash.UserDetails.Dao.Entity.AppUser;
import com.enda.cash.banque.Banque;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Versement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Le montant ne peut pas être vide")
    @Min(value = 1L, message = "La valeur de montant doit être positive ")
    private Double montant;

    @NotNull(message = "Le nmrbulletin ne peut pas être vide")
    @Column(length = 30)
    private String nmrbulletin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime dateVersement;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate FilterDateVersement = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le Status Versement ne peut pas être vide")
    private StatusVersement statutVersement = StatusVersement.En_attente;

    @Column(length = 50, unique = true)
    private String transactionId;


    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private AppUser ap;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private UploadFileResponse imageversement;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Banque banque;

}
