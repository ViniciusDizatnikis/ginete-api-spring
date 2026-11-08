package br.com.ginete.shared.config;

import br.com.ginete.features.auth.application.LoginUseCase;
import br.com.ginete.features.auth.infrastructure.http.AuthController;
import br.com.ginete.features.dashboard.infrastructure.http.DashboardController;
import br.com.ginete.features.youtubemedia.infrastructure.http.webhook.DownloadWebhookController;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configrações de Segurança da Aplicação
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String ADMIN_AUTHORITY = "SCOPE_" + LoginUseCase.ADMIN_SCOPE;

    /**
     * Definir filtro para a aplicação
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, AuthController.LOGIN_PATH).permitAll()
                        .requestMatchers(HttpMethod.POST, DownloadWebhookController.DOWNLOAD_WEBHOOK_PATH).permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/error").permitAll()
                        .requestMatchers("/v1/media/**").hasAuthority(ADMIN_AUTHORITY)
                        // Precisa vir antes do GET /** liberado, senão a rota fica pública
                        .requestMatchers(DashboardController.ADMIN_PATH_BASE + "/**").hasAuthority(ADMIN_AUTHORITY)
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        .anyRequest().hasAuthority(ADMIN_AUTHORITY)
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    /**
     * Define o usuário administrador da aplicação.
     * <p>
     * A senha pode ser em texto puro ou codificada.
     * Caso esteja codificada (com identificador de algoritmo), é armazenada
     * diretamente. Caso contrário, é gerado um hash do encriptador padrão definido antes de armazená-la
     * em memória.
     */
    @Bean
    public UserDetailsService userDetailsService(
            @Value("${ginete.auth.admin.username}") String username,
            @Value("${ginete.auth.admin.password}") String password
    ) {
        String storedPassword = password.startsWith("{")
                ? password
                : passwordEncoder().encode(password);

        return new InMemoryUserDetailsManager(User.withUsername(username)
                .password(storedPassword)
                .build());
    }

    /**
     * Encriptador de senha
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /**
     * Definindo o mecanismo de authenticação que o spring vai ysar
     */
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    /**
     * Configurando o CORS da aplicação
     */
    @Bean
    public WebMvcConfigurer corsConfigurer(
            @Value("${ginete.cors.allowed-origins}") String[] allowedOrigins
    ) {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods(
                                "GET",
                                "POST",
                                "PUT",
                                "PATCH",
                                "DELETE",
                                "OPTIONS"
                        )
                        .allowedHeaders("*");
            }
        };
    }
}
