package br.com.ginete.shared.exception;

import org.springframework.http.HttpStatus;

public abstract class ForbiddenException extends ApiException {

    protected ForbiddenException(String title, String message) {
        super(HttpStatus.FORBIDDEN, title, message, null);
    }
}
