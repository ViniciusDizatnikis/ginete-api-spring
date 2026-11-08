package br.com.ginete.features.auth.domain;

import br.com.ginete.shared.exception.UnauthorizedException;

/**
 * Mensagem genérica de propósito: não revelar se o erro foi no usuário ou na senha.
 */
public class InvalidCredentialsException extends UnauthorizedException {

    public InvalidCredentialsException() {
        super("Invalid Credentials", "Invalid username or password.");
    }
}
