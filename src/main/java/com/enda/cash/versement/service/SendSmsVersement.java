package com.enda.cash.versement.service;

import com.enda.cash.versement.dao.entity.ResponseVersementT24;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SendSmsVersement {
    @Value("${url.NgTrend}")
    private String Url_Ng_Trend;

    String wbLogin = "ENDA01";
    String wbPwd = "tygrm7Am";
    String wbAccount = "ENDA";
    String label = "ENDATAMWEEL"; // source label
    String reference = "EndaCash"; // sending reference
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void RestTemplate_SendSmsVersement(String msg, String dest_num) {

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

// create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

// build the request
        HttpEntity request = new HttpEntity(headers);


        String urlTemplate = UriComponentsBuilder.fromHttpUrl(Url_Ng_Trend)
                .queryParam("login", "{login}")
                .queryParam("pass", "{pass}")
                .queryParam("compte", "{compte}")
                .queryParam("op", "{op}")
                .queryParam("dest_num", "{dest_num}")
                .queryParam("msg", "{msg}")
                .queryParam("type", "{type}")
                .queryParam("dt", "{dt}")
                .queryParam("hr", "{hr}")
                .queryParam("mn", "{mn}")
                .queryParam("label", "{label}")
                .queryParam("ref", "{ref}")
                .encode()
                .toUriString();

        Map<String, String> params = new HashMap<>();
        params.put("login", wbLogin);
        params.put("pass", wbPwd);
        params.put("compte", wbAccount);
        params.put("op", "1");
        params.put("dest_num", dest_num);
        params.put("msg", msg);
        params.put("type", "1");

        LocalDate date = LocalDate.now();
        params.put("dt", fmt.format(date));
        params.put("hr", "00");
        params.put("mn", "00");
        params.put("label", label);
        params.put("ref", reference);


// make an HTTP GET request with headers
        ResponseEntity<ResponseVersementT24> response = restTemplate.exchange(
                urlTemplate,
                HttpMethod.POST,
                request,
                ResponseVersementT24.class,
                params
        );
        System.out.print(response.getHeaders());
// check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
            System.out.println(response.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
    }

    class NgTrendModel {
        String wbLogin = "ENDA01";
        String wbPwd = "tygrm7Am";
        String wbAccount = "ENDA";

    }

}
