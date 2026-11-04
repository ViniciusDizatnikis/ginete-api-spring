package br.com.rincaosonoro.ginete.features.youtubemedia.domain;

import br.com.rincaosonoro.ginete.shared.exception.ServiceUnavailableException;

public class MediaServiceUnavailableException extends ServiceUnavailableException {

    public MediaServiceUnavailableException(Throwable cause) {
        super("Media Service Unavailable", "Media service is currently unavailable.", cause);
    }
}
