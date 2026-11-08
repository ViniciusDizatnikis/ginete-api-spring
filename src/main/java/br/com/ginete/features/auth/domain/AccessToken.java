package br.com.ginete.features.auth.domain;

import java.time.Instant;

public record AccessToken(
        String value,
        Instant expiresAt
) {
}
