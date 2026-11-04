package br.com.rincaosonoro.ginete.shared.exception;

import br.com.rincaosonoro.ginete.features.artists.domain.ArtistId;
import br.com.rincaosonoro.ginete.features.artists.domain.ArtistNotFoundException;
import br.com.rincaosonoro.ginete.features.songs.domain.SongId;
import br.com.rincaosonoro.ginete.features.songs.domain.SongNotFoundException;
import br.com.rincaosonoro.ginete.features.youtubemedia.domain.MediaServiceUnavailableException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTests {
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new FailingController())
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();

    @Test
    void artistNotFoundReturnsProblemDetail() throws Exception {
        mockMvc.perform(get("/artist"))
                .andExpect(status().isNotFound())
                .andExpect(isProblem(404, "Artist Not Found"))
                .andExpect(jsonPath("$.detail").value("Artist not found: 1"));
    }

    @Test
    void songNotFoundReturnsProblemDetail() throws Exception {
        mockMvc.perform(get("/song"))
                .andExpect(status().isNotFound())
                .andExpect(isProblem(404, "Song Not Found"))
                .andExpect(jsonPath("$.detail").value("Song not found: 1"));
    }

    @Test
    void mediaServiceUnavailableReturnsProblemDetail() throws Exception {
        mockMvc.perform(get("/media"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(isProblem(503, "Media Service Unavailable"))
                .andExpect(jsonPath("$.detail").value("Media service is currently unavailable."));
    }

    @Test
    void unexpectedExceptionDoesNotLeakItsMessage() throws Exception {
        mockMvc.perform(get("/unexpected"))
                .andExpect(status().isInternalServerError())
                .andExpect(isProblem(500, "Internal Server Error"))
                .andExpect(jsonPath("$.detail").value("An internal server error occurred."));
    }

    @Test
    void missingParameterReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/params"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void invalidParameterTypeReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/params").param("limit", "abc"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void unsupportedMethodReturnsMethodNotAllowed() throws Exception {
        mockMvc.perform(post("/params"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON));
    }

    private ResultMatcher isProblem(int status, String title) {
        return result -> {
            content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON).match(result);
            jsonPath("$.status").value(status).match(result);
            jsonPath("$.title").value(title).match(result);
        };
    }

    @RestController
    static class FailingController {

        @GetMapping("/artist")
        String artist() {
            throw new ArtistNotFoundException(new ArtistId("1"));
        }

        @GetMapping("/song")
        String song() {
            throw new SongNotFoundException(new SongId("1"));
        }

        @GetMapping("/media")
        String media() {
            throw new MediaServiceUnavailableException(new IllegalStateException("segredo interno"));
        }

        @GetMapping("/unexpected")
        String unexpected() {
            throw new IllegalStateException("segredo interno");
        }

        @GetMapping("/params")
        String params(@RequestParam int limit) {
            return "ok";
        }
    }
}
