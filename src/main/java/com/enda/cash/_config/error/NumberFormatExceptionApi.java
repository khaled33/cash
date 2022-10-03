package com.enda.cash._config.error;

import org.springframework.http.HttpStatus;

public class NumberFormatExceptionApi extends ApiBaseException {


    public NumberFormatExceptionApi(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
