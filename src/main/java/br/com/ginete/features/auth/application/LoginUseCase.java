package br.com.ginete.features.auth.application;

import br.com.ginete.features.auth.domain.AccessToken;
import br.com.ginete.features.auth.domain.AdminSessionRepository;
import br.com.ginete.features.auth.domain.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class LoginUseCase {
    public static final String ADMIN_SCOPE = "admin";

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final AdminSessionRepository adminSessionRepository;
    private final String issuer;
    private final Duration expiration;

    public LoginUseCase(
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            AdminSessionRepository adminSessionRepository,
            @Value("${ginete.auth.jwt.issuer}") String issuer,
            @Value("${ginete.auth.jwt.expiration}") Duration expiration
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.adminSessionRepository = adminSessionRepository;
        this.issuer = issuer;
        this.expiration = expiration;
    }

    public AccessToken execute(String username, String password) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(username, password)
            );
        } catch (AuthenticationException exception) {
            throw new InvalidCredentialsException();
        }

        Instant now = Instant.now();
        Instant expiresAt = now.plus(expiration);

        String sessionId = UUID.randomUUID().toString();
        adminSessionRepository.save(authentication.getName(), sessionId, expiration);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .id(sessionId)
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(authentication.getName())
                .claim("scope", ADMIN_SCOPE)
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
        return new AccessToken(token, expiresAt);
    }
}
