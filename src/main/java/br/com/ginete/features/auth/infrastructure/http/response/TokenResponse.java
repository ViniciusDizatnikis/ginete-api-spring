package br.com.ginete.features.auth.infrastructure.http.response;

import br.com.ginete.features.auth.domain.AccessToken;

import java.time.Instant;

public record TokenResponse(
        String accessToken,
        String tokenType,
        Instant expiresAt
) {
    public static TokenResponse from(AccessToken token) {
        return new TokenResponse(token.value(), "Bearer", token.expiresAt());
    }
}
