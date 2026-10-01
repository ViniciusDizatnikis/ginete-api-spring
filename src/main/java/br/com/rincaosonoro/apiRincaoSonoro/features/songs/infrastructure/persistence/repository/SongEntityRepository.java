package br.com.rincaosonoro.apiRincaoSonoro.features.songs.infrastructure.persistence.repository;

import br.com.rincaosonoro.apiRincaoSonoro.features.songs.infrastructure.persistence.entity.SongEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongEntityRepository extends MongoRepository<SongEntity, String> {
}
