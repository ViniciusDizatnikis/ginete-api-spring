package br.com.rincaosonoro.ginete.features.songs.infrastructure.persistence.repository;

import br.com.rincaosonoro.ginete.features.artists.domain.ArtistId;
import br.com.rincaosonoro.ginete.features.songs.domain.Song;
import br.com.rincaosonoro.ginete.features.songs.domain.SongId;
import br.com.rincaosonoro.ginete.features.songs.domain.SongRepository;
import br.com.rincaosonoro.ginete.features.songs.infrastructure.persistence.entity.SongEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Repository
@RequiredArgsConstructor
public class MongoSongRepository implements SongRepository {
    private final SongEntityRepository songEntityRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<Song> findById(SongId id) {
        return songEntityRepository.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public List<Song> findAllById(List<SongId> ids) {
        var rawIds = ids.stream().map(SongId::value).toList();

        return songEntityRepository.findAllById(rawIds)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Song> findAll(int limit, int offset, String search) {
        Query query = new Query();

        if (search != null && !search.isBlank()) {
            query.addCriteria(
                    Criteria.where("name")
                            .regex(Pattern.quote(search), "i")
            );
        }

        query.skip(offset);
        query.limit(limit);

        query.with(Sort.by(Sort.Direction.ASC, "name"));

        return find(query);
    }

    @Override
    public List<Song> findRecent(Instant since, int limit, int offset) {
        Query query = new Query();

        query.addCriteria(
                Criteria.where("createdAt")
                        .ne(null)
                        .gte(since)
        );

        query.skip(offset);
        query.limit(limit);

        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

        return find(query);
    }

    @Override
    public List<Song> findFeatured(int limit, int offset) {
        Query query = new Query();

        query.addCriteria(
                Criteria.where("featured")
                        .is(true)
        );

        query.skip(offset);
        query.limit(limit);

        return find(query);
    }

    @Override
    public List<Song> findRandomSongs(int limit) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sample(limit)
        );

        return mongoTemplate
                .aggregate(aggregation, SongEntity.class, SongEntity.class)
                .getMappedResults()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private List<Song> find(Query query) {
        return mongoTemplate
                .find(query, SongEntity.class)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private Song toDomain(SongEntity entity) {
        var song = new Song();

        song.setId(new SongId(entity.getId()));
        song.setName(entity.getName());
        song.setImage(toUri(entity.getImage()));
        song.setDuration(entity.getDuration() != null ? Duration.ofSeconds(entity.getDuration()) : null);
        song.setArtist(entity.getArtist() != null ? new ArtistId(entity.getArtist()) : null);
        song.setAudio(toUri(entity.getAudio()));
        song.setFeatured(entity.isFeatured());
        song.setCreatedAt(entity.getCreatedAt());
        song.setUpdatedAt(entity.getUpdatedAt());

        return song;
    }

    private static URI toUri(String value) {
        return value != null ? URI.create(value) : null;
    }
}
