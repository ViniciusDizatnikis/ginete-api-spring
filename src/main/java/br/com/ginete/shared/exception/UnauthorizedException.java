package br.com.ginete.shared.exception;

import org.springframework.http.HttpStatus;

public abstract class UnauthorizedException extends ApiException {

    protected UnauthorizedException(String title, String message) {
        super(HttpStatus.UNAUTHORIZED, title, message, null);
    }
}
