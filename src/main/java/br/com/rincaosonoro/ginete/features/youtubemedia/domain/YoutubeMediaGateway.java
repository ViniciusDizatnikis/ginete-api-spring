package br.com.rincaosonoro.ginete.features.youtubemedia.domain;

public interface YoutubeMediaGateway {
    MediaHealth check();
    YoutubeVideoMetadata getMetadata(String url);
    MediaId downloadMedia(String url, String webhookUrl);
}
