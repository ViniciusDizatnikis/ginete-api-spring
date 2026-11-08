package br.com.ginete.features.auth.domain;

import java.time.Duration;

public interface AdminSessionRepository {
    void save(String username, String sessionId , Duration duration);
    boolean exists(String username, String sessionId);
    void delete(String username);
}
