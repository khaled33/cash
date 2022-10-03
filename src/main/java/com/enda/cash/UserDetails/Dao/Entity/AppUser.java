package com.enda.cash.UserDetails.Dao.Entity;

import com.enda.cash.agence.Agences;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "User")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AppUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Le email ne peut pas être vide")
    @Column(length = 30, unique = true)
    private String email;

    //    @NotNull(message = "Le password ne peut pas être vide")
    private String password;

    /*
        @NotNull(message = "Le username ne peut pas être vide")
    */
    @Column(length = 50, unique = true)
    private String username;

    //    @NotNull(message = "Le code_pin ne peut pas être vide")
//    @Min(value = 10000, message = "Le code_pin doit être 5 chiffre")
//    @Max(value = 99999, message = "Le code_pin doit être 5 chiffre")
    @Column(length = 5)
    private int code_pin;

    @NotNull(message = "Le nom ne peut pas être vide")
    @Column(length = 50)
    private String nom;

    @NotNull(message = "Le prenom ne peut pas être vide")
    @Column(length = 50)
    private String prenom;

    @NotNull(message = "Le cin ne peut pas être vide")
    @Min(value = 10000000, message = "Le cin doit être 8 chiffre")
    @Max(value = 99999999, message = "Le cin doit être 8 chiffre")
    @Column(length = 8)
    private Integer cin;

    @NotNull(message = "Le tel ne peut pas être vide")
    @Min(value = 10000000, message = "Le tel doit être 8 chiffre")
    @Max(value = 99999999, message = "Le tel doit être 8 chiffre")
    @Column(length = 8)
    private Integer tel;


    @Column(columnDefinition = "TINYINT(1)", precision = 0)
    private Boolean pwd_changed;
    @Column(columnDefinition = "TINYINT(1)", precision = 0)
    private Boolean pin_changed;

    @Column(columnDefinition = "TINYINT(1)", precision = 1)
    private Boolean enable;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private Date created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private Date modified;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH})
    private List<Role> roles = new ArrayList<>();


    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Agences agence;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonSetter
    public void setPassword(String password) {
        this.password = password;
    }
}
