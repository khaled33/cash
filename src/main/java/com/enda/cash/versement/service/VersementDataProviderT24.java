package com.enda.cash.versement.service;

import com.enda.cash.versement.dao.entity.ResponseVersementT24;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class VersementDataProviderT24 {

    @Value("${url.t24}")
    private String UrlT24;

    public void RestTemplate_insert_Versement(String UrlParam) {
        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(UrlT24);
// create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

// build the request
        HttpEntity request = new HttpEntity(headers);

// make an HTTP GET request with headers
        ResponseEntity<ResponseVersementT24> response = restTemplate.exchange(
                UrlT24.concat("Versement" + UrlParam),
                HttpMethod.GET,
                request,
                ResponseVersementT24.class
        );
        System.out.println(response.getBody());

// check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
            System.out.println(response.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
    }

}
