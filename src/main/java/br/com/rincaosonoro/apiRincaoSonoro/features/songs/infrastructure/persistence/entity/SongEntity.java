package br.com.rincaosonoro.apiRincaoSonoro.features.songs.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "songs")
public class SongEntity {
    @Id
    private String id;

    private String image;

    private String name;

    private Long duration;

    private String artist;

    private String audio;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
