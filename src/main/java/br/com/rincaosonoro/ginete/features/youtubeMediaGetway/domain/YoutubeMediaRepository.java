package br.com.rincaosonoro.ginete.features.youtubeMediaGetway.domain;

public interface YoutubeMediaRepository {
    MediaHealth check();
    YoutubeVideoMetadata getMetadata(String url);
}
