package br.com.ginete.features.auth.infrastructure.http;

import br.com.ginete.features.auth.application.LoginUseCase;
import br.com.ginete.features.auth.application.LogoutUseCase;
import br.com.ginete.features.auth.infrastructure.http.request.LoginRequest;
import br.com.ginete.features.auth.infrastructure.http.response.TokenResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static br.com.ginete.shared.config.openAPIConfig.BEARER_AUTH;
import static br.com.ginete.shared.config.openAPIConfig.TAG_AUTH;

@RestController
@Tag(name = TAG_AUTH)
@RequiredArgsConstructor
public class AuthController {
    public static final String AUTH_PATH_BASE = "/v1/auth";

    public static final String LOGIN_PATH = AUTH_PATH_BASE + "/login";
    public static final String LOGOUT_PATH = AUTH_PATH_BASE + "/logout";

    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;

    @PostMapping(LOGIN_PATH)
    public ResponseEntity<TokenResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(TokenResponse.from(
                loginUseCase.execute(request.username(), request.password())
        ));
    }

    @PostMapping(LOGOUT_PATH)
    @SecurityRequirement(name = BEARER_AUTH)
    public ResponseEntity<Void> logout(@AuthenticationPrincipal Jwt jwt) {
        logoutUseCase.execute(jwt);
        return ResponseEntity.noContent().build();
    }

}
