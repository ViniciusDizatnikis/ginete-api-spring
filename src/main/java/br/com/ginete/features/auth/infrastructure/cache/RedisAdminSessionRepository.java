package br.com.ginete.features.auth.infrastructure.cache;

import br.com.ginete.features.auth.domain.AdminSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisAdminSessionRepository implements AdminSessionRepository {
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void save(String username, String sessionId, Duration duration) {
        stringRedisTemplate.opsForValue().set(getKey(username), sessionId, duration);
    }

    @Override
    public boolean exists(String username, String sessionId) {
        String currentSessionId = stringRedisTemplate.opsForValue().get(getKey(username));
        return sessionId.equals(currentSessionId);
    }

    @Override
    public void delete(String username) {
        stringRedisTemplate.delete(getKey(username));
    }

    private String getKey(String username) {
        return "auth:session:" + username;
    }
}
