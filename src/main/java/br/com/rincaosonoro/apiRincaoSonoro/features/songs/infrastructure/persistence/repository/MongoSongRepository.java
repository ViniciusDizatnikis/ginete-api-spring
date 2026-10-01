package br.com.rincaosonoro.apiRincaoSonoro.features.songs.infrastructure.persistence.repository;

import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.ArtistId;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.Song;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.SongId;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.SongNotFoundException;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.SongRepository;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.infrastructure.persistence.entity.SongEntity;
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
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MongoSongRepository implements SongRepository {
    private final SongEntityRepository songEntityRepository;
    private final MongoTemplate mongoTemplate;


    @Override
    public List<Song> findAll(Integer limit, Integer offset, String search) {
        if (limit == null) limit = 15;
        if (offset == null) offset = 0;

        Query query = new Query();

        if (search != null && !search.isBlank()) {
            query.addCriteria(Criteria.where("name").regex(search, "i"));
        }

        query.skip(offset);
        query.limit(limit);

        query.with(Sort.by(Sort.Direction.ASC, "name"));

        return mongoTemplate.find(query, SongEntity.class)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Song findById(SongId id) {
        SongEntity songEntity = songEntityRepository.findById(id.value())
                .orElseThrow(() -> new SongNotFoundException(id));

        return toDomain(songEntity);
    }

    @Override
    public List<Song> findAllById(List<String> ids) {
        return songEntityRepository.findAllById(ids).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Song> findRecent(Integer limit, Integer offset) {
        if (limit == null) limit = 15;
        if (offset == null) offset = 0;

        Instant oneWeekAgo = Instant.now().minus(7, ChronoUnit.DAYS);

        Query query = new Query();

        query.addCriteria(
                Criteria.where("createdAt")
                        .ne(null)
                        .gte(oneWeekAgo)
        );

        query.limit(limit);
        query.skip(offset);

        query.with(
                Sort.by(
                        Sort.Direction.ASC,
                        "createdAt"
                )
        );
        return mongoTemplate
                .find(query, SongEntity.class)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Song> findFeatured(Integer limit, Integer offset) {
        if (limit == null) limit = 15;
        if (offset == null) offset = 0;

        Query query = new Query();

        query.addCriteria(
                Criteria.where("featured").is(true)
        );

        query.limit(limit);
        query.skip(offset);

        return mongoTemplate
                .find(query, SongEntity.class)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Song> findRandomSongs(int limit) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sample(limit)
        );

        return mongoTemplate
                .aggregate(
                        aggregation,
                        "songs",
                        SongEntity.class
                )
                .getMappedResults()
                .stream()
                .map(this::toDomain)
                .toList();
    }


    private Song toDomain(SongEntity entity) {
        var song = new Song();

        song.setId(new SongId(entity.getId()));
        song.setName(entity.getName());
        song.setImage(URI.create(entity.getImage()));
        song.setDuration(Duration.ofSeconds(entity.getDuration()));
        song.setArtist(new ArtistId(entity.getArtist()));
        song.setAudio(URI.create(entity.getAudio()));
        song.setCreatedAt(entity.getCreatedAt());
        song.setUpdatedAt(entity.getUpdatedAt());

        return song;
    }
}
