package br.com.ginete.shared.config;

import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class openAPIConfig {
    public static final String BEARER_AUTH = "bearerAuth";

    public static final String TAG_AUTH = "Auth";
    public static final String TAG_MEDIA = "Youtube Media";
    public static final String TAG_SONGS = "Songs";
    public static final String TAG_ARTISTS = "Artists";
    public static final String TAG_DASHBOARD = "Dashboard";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ginete Api")
                )
                .components(new Components()
                        .addSecuritySchemes(BEARER_AUTH, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addTagsItem(new Tag().name(TAG_AUTH).description("Login e logout do administrador"))
                .addTagsItem(new Tag().name(TAG_SONGS).description("Músicas"))
                .addTagsItem(new Tag().name(TAG_ARTISTS).description("Artistas"))
                .addTagsItem(new Tag().name(TAG_MEDIA).description("Download de mídia do YouTube (admin)"))
                .addTagsItem(new Tag().name(TAG_DASHBOARD).description("Estatísticas do painel (admin)"));
    }

}
