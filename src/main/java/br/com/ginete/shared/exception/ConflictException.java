package br.com.ginete.shared.exception;

import org.springframework.http.HttpStatus;

public abstract class ConflictException extends ApiException {

    protected ConflictException(String title, String message) {
        super(HttpStatus.CONFLICT, title, message, null);
    }
}
