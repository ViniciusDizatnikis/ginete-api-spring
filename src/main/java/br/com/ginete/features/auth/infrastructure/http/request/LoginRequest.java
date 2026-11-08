package br.com.ginete.features.auth.infrastructure.http.request;

public record LoginRequest(
        String username,
        String password
) {
}
