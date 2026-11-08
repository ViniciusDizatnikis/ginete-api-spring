package br.com.ginete.features.dashboard.application;

import br.com.ginete.features.artists.domain.ArtistRepository;
import br.com.ginete.features.dashboard.domain.DashboardStats;
import br.com.ginete.features.songs.domain.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDashboardStatsUseCase {
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public DashboardStats execute() {
        return new DashboardStats(
                songRepository.count(),
                artistRepository.count()
        );
    }
}
