package com.enda.cash.remboursement.dao.entity;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResponseQueryGetLoansAAT24 {
    private String govid;
    private String customerid;
    private List<String> arrangements;
    @JsonSetter("Status")
    private String Status;

}
