package br.com.curadoria.core.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EnderecoInvalidoException extends RuntimeException {

    public EnderecoInvalidoException(String message) {
        super(message);
    }

    public EnderecoInvalidoException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
