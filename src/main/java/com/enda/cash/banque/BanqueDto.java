package com.enda.cash.banque;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BanqueDto {
    private List<Banque> banques;
    private Banque post;
}
