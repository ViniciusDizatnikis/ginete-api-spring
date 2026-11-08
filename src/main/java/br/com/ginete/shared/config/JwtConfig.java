package br.com.ginete.shared.config;

import br.com.ginete.features.auth.domain.AdminSessionRepository;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Configuração do JWT
 */
@Configuration
public class JwtConfig {
    private static final int MIN_SECRET_BYTES = 32;

    /**
     * Configurando a chave do JWT
     */
    @Bean
    public SecretKey jwtSecretKey(@Value("${ginete.auth.jwt.secret}") String secret) {
        byte[] bytes = Base64.getDecoder().decode(secret);

        if (bytes.length < MIN_SECRET_BYTES) {
            throw new IllegalStateException("JWT_SECRET precisa ter pelo menos 256 bits (32 bytes) para HS256.");
        }

        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    /**
     * Configurando Encoder do JWT
     */
    @Bean
    public JwtEncoder jwtEncoder(SecretKey jwtSecretKey) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecretKey));
    }

    /**
     * Configurando o Decoder do JWT
     */
    @Bean
    public JwtDecoder jwtDecoder(
            SecretKey jwtSecretKey,
            @Value("${ginete.auth.jwt.issuer}") String issuer,
            AdminSessionRepository adminSessionRepository
    ) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(jwtSecretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();

        OAuth2TokenValidator<Jwt> sessionValidator = jwt ->
                jwt.getId() != null && adminSessionRepository.exists(jwt.getSubject(), jwt.getId())
                        ? OAuth2TokenValidatorResult.success()
                        : OAuth2TokenValidatorResult.failure(
                        new OAuth2Error("invalid_token", "Session expired or revoked", null)
                );


        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                JwtValidators.createDefaultWithIssuer(issuer),
                sessionValidator
        ));

        return decoder;
    }
}
