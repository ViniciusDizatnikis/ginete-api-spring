package br.com.rincaosonoro.ginete.features.songs.infrastructure.persistence.repository;

import br.com.rincaosonoro.ginete.features.songs.infrastructure.persistence.entity.SongEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SongEntityRepository extends MongoRepository<SongEntity, String> {}
