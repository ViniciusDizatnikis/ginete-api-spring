package br.com.rincaosonoro.ginete.shared.exception;

import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {
    private final String title;

    protected ApiException(String title, String message) {
        super(message);
        this.title = title;
    }

    protected ApiException(String title, String message, Throwable cause) {
        super(message, cause);
        this.title = title;
    }
}
