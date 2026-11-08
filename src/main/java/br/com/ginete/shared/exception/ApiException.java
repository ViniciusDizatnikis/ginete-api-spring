package br.com.ginete.shared.exception;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Base de todo erro esperado da API. O GlobalExceptionHandler transforma qualquer subclasse em
 * application/problem+json com status, title, detail (a mensagem) e code, sem precisar de handler novo.
 * Para criar um erro, estenda a base do status (NotFoundException, ConflictException...).
 */
@Getter
public abstract class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String title;

    @Getter(AccessLevel.NONE)
    private final Map<String, Object> properties = new LinkedHashMap<>();

    protected ApiException(HttpStatus status, String title, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.title = title;
    }

    /**
     * Código estável para o front tratar o erro: ArtistNotFoundException vira "ARTIST_NOT_FOUND".
     */
    public String getCode() {
        String name = getClass().getSimpleName().replaceFirst("Exception$", "");

        return name.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toUpperCase();
    }

    /**
     * Campos extras no corpo do erro, além de title, status, detail e code.
     */
    public Map<String, Object> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    protected void addProperty(String name, Object value) {
        properties.put(name, value);
    }
}
