package br.com.ginete.features.youtubemedia.domain;

import br.com.ginete.shared.exception.ServiceUnavailableException;

public class MediaServiceUnavailableException extends ServiceUnavailableException {

    public MediaServiceUnavailableException(Throwable cause) {
        super("Media Service Unavailable", "Media service is currently unavailable.", cause);
    }
}
