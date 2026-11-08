package br.com.ginete.features.youtubemedia.domain;

public interface YoutubeMediaGateway {
    MediaHealth check();
    YoutubeVideoMetadata getMetadata(String url);
    DownloadId downloadMedia(String url, String webhookUrl);
}
