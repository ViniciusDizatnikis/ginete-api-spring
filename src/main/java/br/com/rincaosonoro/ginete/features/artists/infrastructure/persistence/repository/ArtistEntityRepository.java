package br.com.rincaosonoro.ginete.features.artists.infrastructure.persistence.repository;

import br.com.rincaosonoro.ginete.features.artists.infrastructure.persistence.entity.ArtistEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArtistEntityRepository extends MongoRepository<ArtistEntity, String> {}
