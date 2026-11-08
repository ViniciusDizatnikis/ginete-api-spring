package br.com.ginete.shared.exception;

import org.springframework.http.HttpStatus;

public abstract class ServiceUnavailableException extends ApiException {

    protected ServiceUnavailableException(String title, String message, Throwable cause) {
        super(HttpStatus.SERVICE_UNAVAILABLE, title, message, cause);
    }
}
