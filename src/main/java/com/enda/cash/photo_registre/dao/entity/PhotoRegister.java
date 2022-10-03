package com.enda.cash.photo_registre.dao.entity;

import com.enda.cash.UserDetails.Dao.Entity.AppUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhotoRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")

    private LocalDateTime dateCreation;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private AppUser ap;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private UploadFileResponseRegisterImage imageRegister;
}
