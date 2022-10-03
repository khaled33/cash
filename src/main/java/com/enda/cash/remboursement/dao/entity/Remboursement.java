package com.enda.cash.remboursement.dao.entity;

import com.enda.cash.UserDetails.Dao.Entity.AppUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
public class Remboursement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Le codeAA ne peut pas être vide")
    @Column(length = 30)
    private String codeAA;

    @NotNull(message = "Le montant ne peut pas être vide")
    @Min(value = 1, message = "Le montant doit être un chiffre positive")
    private Double montant;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime dateTransactionRemboursement;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonIgnore
    private LocalDate dateRemboursement;

    @NotNull(message = "Date ne peut pas être vide")

    private String date;

    @Column(length = 20)
    private String statutTransactionT24;

    @Transient
    private String nextRepayDate;

    @Column(unique = true, length = 50)
    private String transactionId;

    @NotNull(message = "Le cin ne peut pas être vide")
/*    @Min(value = 10000000, message = "Le cin doit être 8 chiffre")
    @Max(value = 99999999, message = "Le cin doit être 8 chiffre")*/
    @Column(length = 8)
    private String cinClient;

    @NotNull(message = "Le Pin ne peut pas être vide")
    @Min(value = 10000, message = "Le Pin doit être 5 chiffre")
    @Max(value = 99999, message = "Le Pin doit être 5 chiffre")
    @Transient
    private Integer PinAp;

    @NotNull(message = "Le tel Client ne peut pas être vide")
    @Min(value = 10000000, message = "Le telClient doit être 8 chiffre")
    @Max(value = 99999999, message = "Le telClient doit être 8 chiffre")
    @Transient
    private Integer telClient;

    @Transient
    private float comission = 0.750f;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private AppUser ap;

    @Column(unique = true, length = 50, nullable = true)
    private String transactionIdEdp;

    @Column(length = 50, nullable = true)
    private String userNameEdp;
}
