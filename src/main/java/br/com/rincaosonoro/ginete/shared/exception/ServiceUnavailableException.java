package br.com.rincaosonoro.ginete.shared.exception;

public abstract class ServiceUnavailableException extends ApiException {

    protected ServiceUnavailableException(String title, String message, Throwable cause) {
        super(title, message, cause);
    }
}
