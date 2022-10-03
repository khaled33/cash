package com.enda.cash._config.error;

import org.springframework.http.HttpStatus;

public class DuplicateEntryException extends ApiBaseException {

    public DuplicateEntryException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
