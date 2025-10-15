package br.com.curadoria.core.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MensagemException extends RuntimeException {
    private HttpStatus codigoErro;

    public MensagemException(String message, HttpStatus codigoErro) {
        super(message);
        setCodigoErro(codigoErro);
    }

    public MensagemException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
