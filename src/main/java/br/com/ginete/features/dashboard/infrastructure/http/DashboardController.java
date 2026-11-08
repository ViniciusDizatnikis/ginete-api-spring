package br.com.ginete.features.dashboard.infrastructure.http;

import br.com.ginete.features.dashboard.application.GetDashboardStatsUseCase;
import br.com.ginete.features.dashboard.infrastructure.http.response.DashboardStatsResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.ginete.shared.config.openAPIConfig.BEARER_AUTH;
import static br.com.ginete.shared.config.openAPIConfig.TAG_DASHBOARD;

@RestController
@Tag(name = TAG_DASHBOARD)
@SecurityRequirement(name = BEARER_AUTH)
@RequiredArgsConstructor
public class DashboardController {
    public static final String ADMIN_PATH_BASE = "/v1/admin";

    public static final String STATS_PATH = ADMIN_PATH_BASE + "/stats";

    private final GetDashboardStatsUseCase getDashboardStatsUseCase;

    @GetMapping(STATS_PATH)
    public ResponseEntity<DashboardStatsResponse> getStats() {
        return ResponseEntity.ok(DashboardStatsResponse.from(getDashboardStatsUseCase.execute()));
    }
}
