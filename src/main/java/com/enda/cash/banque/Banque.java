package com.enda.cash.banque;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Banque implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bank_id;
    private String bankName;
    private boolean isActive;
    private int type;
}
