package br.com.rincaosonoro.ginete.shared.exception;

public abstract class NotFoundException extends ApiException {

    protected NotFoundException(String title, String message) {
        super(title, message);
    }
}
