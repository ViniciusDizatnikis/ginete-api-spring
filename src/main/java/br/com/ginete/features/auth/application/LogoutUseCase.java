package br.com.ginete.features.auth.application;

import br.com.ginete.features.auth.domain.AdminSessionRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class LogoutUseCase {
    AdminSessionRepository adminSessionRepository;

    public LogoutUseCase(AdminSessionRepository adminSessionRepository) {
        this.adminSessionRepository = adminSessionRepository;
    }

    public void execute(Jwt jwt) {
        adminSessionRepository.delete(jwt.getSubject());
    }
}
