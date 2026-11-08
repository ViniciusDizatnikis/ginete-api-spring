package br.com.ginete.shared.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Toda ApiException vira application/problem+json pelo status que ela mesma declara; erros do Spring MVC
 * (JSON inválido, parâmetro faltando...) ficam com o ResponseEntityExceptionHandler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String CODE_PROPERTY = "code";

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ProblemDetail> handleApiException(ApiException exception) {
        if (exception.getStatus().is5xxServerError()) {
            log.warn("{}: {}", exception.getTitle(), exception.getMessage(), exception);
        } else {
            log.debug("{}: {}", exception.getTitle(), exception.getMessage());
        }

        ProblemDetail problem = problem(exception.getStatus(), exception.getTitle(), exception.getMessage(), exception.getCode());

        exception.getProperties().forEach(problem::setProperty);

        return ResponseEntity
                .status(exception.getStatus())
                .body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception exception) {
        log.error("Erro interno no servidor", exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problem(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal Server Error",
                        "An internal server error occurred.",
                        "INTERNAL_SERVER_ERROR"
                ));
    }

    private static ProblemDetail problem(HttpStatus status, String title, String detail, String code) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);

        problem.setTitle(title);
        problem.setProperty(CODE_PROPERTY, code);

        return problem;
    }
}
