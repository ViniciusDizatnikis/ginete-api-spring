package br.com.rincaosonoro.apiRincaoSonoro.features.artists.infrastructure.persistence.repository;

import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.Artist;
import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.ArtistId;
import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.ArtistNotFoundException;
import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.ArtistRepository;
import br.com.rincaosonoro.apiRincaoSonoro.features.artists.infrastructure.persistence.entity.ArtistEntity;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.infrastructure.persistence.entity.SongEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public class MongoArtistRepository implements ArtistRepository {

    private final ArtistEntityRepository artistEntityRepository;
    private final MongoTemplate mongoTemplate;

    public MongoArtistRepository(ArtistEntityRepository artistEntityRepository, MongoTemplate mongoTemplate) {
        this.artistEntityRepository = artistEntityRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Artist findById(ArtistId id) {
        ArtistEntity artist = artistEntityRepository.findById(id.value())
                .orElseThrow(() -> new ArtistNotFoundException(id));

        return toDomain(artist);
    }

    @Override
    public List<Artist> findAll(Integer limit, Integer offset, String search) {
        if (limit == null) limit = 15;
        if (offset == null) offset = 0;

        Query query = new Query();

        if (search != null && !search.isBlank()) {
            query.addCriteria(
                    Criteria.where("name")
                            .regex(search, "i")
            );
        }

        query.skip(offset);
        query.limit(limit);

        query.with(Sort.by(Sort.Direction.ASC, "name"));

        return mongoTemplate.find(query, ArtistEntity.class)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Artist> findRecent(Integer limit, Integer offset) {
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
                .find(query, ArtistEntity.class)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Artist> findFeatured(Integer limit, Integer offset) {
        if (limit == null) limit = 15;
        if (offset == null) offset = 0;

        Query query = new Query();

        query.addCriteria(
                Criteria.where("featured")
                        .is(true)
        );

        query.limit(limit);
        query.skip(offset);

        return mongoTemplate
                .find(query, ArtistEntity.class)
                .stream()
                .map(this::toDomain)
                .toList();
    }


    private Artist toDomain(ArtistEntity entity) {
        var artist = new Artist();

        artist.setId(new ArtistId(entity.getId()));
        artist.setName(entity.getName());
        artist.setFeatured(entity.isFeatured());
        artist.setImage(URI.create(entity.getImage()));
        artist.setBanner(URI.create(entity.getBanner()));
        artist.setCreatedAt(entity.getCreatedAt());
        artist.setUpdatedAt(entity.getUpdatedAt());

        return artist;
    }
}