package br.com.rincaosonoro.ginete.features.songs.infrastructure.cache;

import br.com.rincaosonoro.ginete.features.songs.domain.TopSongsCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RedisTopSongsCacheRepository implements TopSongsCacheRepository {
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void save(List<String> songIds) {
        String key = getKey();

        stringRedisTemplate.delete(key);

        stringRedisTemplate.opsForList()
                .rightPushAll(key, songIds);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMidnight = now.toLocalDate()
                .plusDays(1)
                .atStartOfDay();

        Duration durationUntilMidnight = Duration.between(now, nextMidnight);

        stringRedisTemplate.expire(key, durationUntilMidnight);
    }


    @Override
    public List<String> get() {

        String key = getKey();

        List<String> ids = stringRedisTemplate
                .opsForList()
                .range(key, 0, -1);

        return ids == null ? List.of() : ids;
    }

    @Override
    public boolean tryLock() {

        Boolean acquired = stringRedisTemplate
                .opsForValue()
                .setIfAbsent(
                        getLockKey(),
                        "locked",
                        Duration.ofSeconds(30)
                );

        return Boolean.TRUE.equals(acquired);
    }

    @Override
    public void unlock() {
        stringRedisTemplate.delete(getLockKey());
    }

    private String getKey() {
        return "songs:top:" + LocalDate.now();
    }

    private String getLockKey() {
        return "songs:top:lock:" + LocalDate.now();
    }
}
