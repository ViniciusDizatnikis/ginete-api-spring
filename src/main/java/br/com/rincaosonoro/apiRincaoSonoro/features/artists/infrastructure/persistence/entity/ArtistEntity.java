package br.com.rincaosonoro.apiRincaoSonoro.features.artists.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "artists")
public class ArtistEntity {

    @Id
    private String id;

    private String name;

    private String image;

    private String banner;

    private boolean featured;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
