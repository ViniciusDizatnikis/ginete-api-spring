package br.com.ginete.shared.exception;

import org.springframework.http.HttpStatus;

public abstract class BadRequestException extends ApiException {

    protected BadRequestException(String title, String message) {
        super(HttpStatus.BAD_REQUEST, title, message, null);
    }

    protected BadRequestException(String title, String message, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, title, message, cause);
    }
}
