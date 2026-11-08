package br.com.ginete.shared.exception;

import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends ApiException {

    protected NotFoundException(String title, String message) {
        super(HttpStatus.NOT_FOUND, title, message, null);
    }
}
