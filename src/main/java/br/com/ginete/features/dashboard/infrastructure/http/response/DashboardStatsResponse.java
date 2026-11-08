package br.com.ginete.features.dashboard.infrastructure.http.response;

import br.com.ginete.features.dashboard.domain.DashboardStats;

public record DashboardStatsResponse(
        long songs,
        long artists
) {
    public static DashboardStatsResponse from(DashboardStats stats) {
        return new DashboardStatsResponse(stats.songs(), stats.artists());
    }
}
