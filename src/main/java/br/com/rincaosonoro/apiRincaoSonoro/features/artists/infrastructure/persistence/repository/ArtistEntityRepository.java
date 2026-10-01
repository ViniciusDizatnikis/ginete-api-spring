package br.com.rincaosonoro.apiRincaoSonoro.features.artists.infrastructure.persistence.repository;

import br.com.rincaosonoro.apiRincaoSonoro.features.artists.infrastructure.persistence.entity.ArtistEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistEntityRepository extends MongoRepository<ArtistEntity, String> {}
