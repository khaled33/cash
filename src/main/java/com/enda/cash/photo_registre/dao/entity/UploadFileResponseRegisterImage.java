package com.enda.cash.photo_registre.dao.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "RegisterImage")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UploadFileResponseRegisterImage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileDownloadUri;
    @Transient
    private String fileType;
    @Column(length = 100000)
    private String Description;
    @Transient
    private long size;


}
