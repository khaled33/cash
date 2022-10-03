package com.enda.cash.remboursement.service;

import com.enda.cash._config.error.NotFoundException;
import com.enda.cash.remboursement.dao.entity.ResponsePostLoansT24;
import com.enda.cash.remboursement.dao.entity.ResponseQeryConsulPayT24;
import com.enda.cash.remboursement.dao.entity.ResponseQueryGetLoansAAT24;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RemboursementDataProviderT24 {

    @Value("${url.t24}")
    private String UrlT24;

    public List<ResponseQeryConsulPayT24> RestTemplate_GetLoansByCin(String Cin) {

        List<ResponseQeryConsulPayT24> responseQeryConsulPayT24s = new ArrayList<>();
        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

// create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

// build the request
        HttpEntity request = new HttpEntity(headers);

// make an HTTP GET request with headers
        try {
            ResponseEntity<ResponseQueryGetLoansAAT24> response = restTemplate.exchange(
                    UrlT24.concat("getLoansAA?govid=" + Cin),
                    HttpMethod.GET,
                    request,
                    ResponseQueryGetLoansAAT24.class);

            // check response
            if (response.getStatusCode() == HttpStatus.OK) {

//  Response Boody
                try {
                    ResponseQueryGetLoansAAT24 responseQueryGetLoansAAT24 = response.getBody();


                    if (responseQueryGetLoansAAT24.getStatus().equals("1")) {
                        for (String CodeAA : responseQueryGetLoansAAT24.getArrangements()) {

                            responseQeryConsulPayT24s.add(this.RestTemplate_ConsulPay(CodeAA));
                        }
                    }
                    return responseQeryConsulPayT24s;

                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                    throw new NotFoundException("errr T24 Response API GetLoans");
                }


            } else {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());
                throw new NotFoundException("errr T24 Response");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new NotFoundException("T24 ne fonctionne pas");
        }


    }

    public ResponseQeryConsulPayT24 RestTemplate_ConsulPay(String codeAA) {
        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

// create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

// build the request
        HttpEntity request = new HttpEntity(headers);

// make an HTTP GET request with headers
        ResponseEntity<ResponseQeryConsulPayT24> response = restTemplate.exchange(
                UrlT24.concat("ConsulPay?loanAccount=" + codeAA),
                HttpMethod.GET,
                request,
                ResponseQeryConsulPayT24.class
        );

// check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
            System.out.println(response.getBody());
            ResponseQeryConsulPayT24 responseQeryConsulPayT24 = response.getBody();
            responseQeryConsulPayT24.setCodeAA(codeAA);
            return response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
            throw new NotFoundException("errr T24 Response");
        }


    }


    public ResponsePostLoansT24 RestTemplate_PostLoansT24(String codeAA, Double Montant, String TransactionID) {
        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

// create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

// build the request
        HttpEntity request = new HttpEntity(headers);

// make an HTTP GET request with headers
        ResponseEntity<ResponsePostLoansT24> response = restTemplate.exchange(
                UrlT24.concat("Repay?loanAccount=" + codeAA + "&amount=" + Montant + "&docId=" + TransactionID),
                HttpMethod.POST,
                request,
                ResponsePostLoansT24.class
        );
        System.out.println(UrlT24.concat("Repay?loanAccount=" + codeAA + "&amount=" + Montant + "&docId=" + TransactionID));
// check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
            System.out.println(response.getBody());

            return response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
            throw new NotFoundException("errr T24 Response");
        }


    }
}
