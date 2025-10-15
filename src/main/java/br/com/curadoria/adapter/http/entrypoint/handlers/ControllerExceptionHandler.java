package br.com.curadoria.adapter.http.entrypoint.handlers;

import java.util.ArrayList;
import java.util.List;

import br.com.curadoria.adapter.http.dto.response.FieldMessage;
import br.com.curadoria.adapter.http.dto.response.Message;
import br.com.curadoria.adapter.http.dto.response.MessageType;
import br.com.curadoria.adapter.http.dto.response.RestResult;
import br.com.curadoria.core.services.exceptions.NotificacoesInvalidasException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.curadoria.core.services.exceptions.DatabaseException;
import br.com.curadoria.core.services.exceptions.ForbiddenException;
import br.com.curadoria.core.services.exceptions.MensagemException;
import br.com.curadoria.core.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger HANDLER_LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    private static final String MSG_INTERNAL_SERVER_ERROR = "Erro interno no servidor.";
    private static final String MSG_NAO_AUTORIZADO = "Usuário não autorizado.";
    private static final String MSG_NAO_ENCONTRADO = "Não encontrado.";
    private static final String MSG_NAO_PROCESSADO = "Não processado.";
    private static final String MSG_EMAIL_CADASTRADO = "Email já cadastrado.";
    private static final String MSG_TELEFONE_CADASTRADO = "Telefone já cadastrado.";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RestResult> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = e.getMessage() == null ? MSG_NAO_ENCONTRADO : e.getMessage();
        List<Message> content = new ArrayList<>();
        content.add(Message.builder().code(status.value()).message(message).messageType(MessageType.ERROR).build());

        return handleException(new RestResult(content), e, status);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<RestResult> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage() == null ? MSG_INTERNAL_SERVER_ERROR : e.getMessage();
        List<Message> content = new ArrayList<>();
        content.add(Message.builder().code(status.value()).message(message).messageType(MessageType.ERROR).build());

        return handleException(new RestResult(content), e, status);
    }

    @ExceptionHandler(NotificacoesInvalidasException.class)
    public ResponseEntity<RestResult> notificacoesNaoEncontradas(NotificacoesInvalidasException e,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage() == null ? MSG_INTERNAL_SERVER_ERROR : e.getMessage();
        List<Message> content = new ArrayList<>();
        content.add(Message.builder().code(status.value()).message(message).messageType(MessageType.ERROR).build());

        return handleException(new RestResult(content), e, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResult> methodArgumentNotValidation(MethodArgumentNotValidException e,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        String message = e.getMessage().isEmpty() ? MSG_NAO_PROCESSADO : e.getMessage();
        List<Message> content = new ArrayList<>();
        List<FieldMessage> campos = new ArrayList<>();
        for (FieldError f : e.getBindingResult().getFieldErrors())
            campos.add(FieldMessage.builder().field(f.getField()).message(f.getDefaultMessage()).build());
        content.add(Message.builder().code(status.value()).fieldMessages(campos).message(message)
                .messageType(MessageType.ERROR).build());

        return handleException(new RestResult(content), e, status);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<RestResult> forbidden(ForbiddenException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        String message = e.getMessage() == null ? MSG_NAO_AUTORIZADO : e.getMessage();
        List<Message> content = new ArrayList<>();
        content.add(Message.builder().code(status.value()).message(message).messageType(MessageType.ERROR).build());

        return handleException(new RestResult(content), e, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResult> internal(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = e.getMessage() == null ? MSG_INTERNAL_SERVER_ERROR : e.getMessage();
        List<Message> content = new ArrayList<>();
        content.add(Message.builder().code(status.value()).message(message).messageType(MessageType.ERROR).build());
        return handleException(new RestResult(content), e, status);
    }

    protected ResponseEntity<RestResult> handleException(RestResult error, Exception exception, HttpStatus status) {
        HANDLER_LOGGER.error(exception.getMessage(), exception);
        return ResponseEntity.status(status).body((error));
    }

    @ExceptionHandler(MensagemException.class)
    public ResponseEntity<RestResult> mensagemCodigoErroException(MensagemException e, HttpServletRequest request) {
        HttpStatus status = e.getCodigoErro() == null ? HttpStatus.CONFLICT : e.getCodigoErro();
        String message = e.getMessage() == null ? MSG_INTERNAL_SERVER_ERROR : e.getMessage();
        List<Message> content = new ArrayList<>();
        content.add(Message.builder().code(status.value()).message(message).messageType(MessageType.ERROR).build());

        return handleException(new RestResult(content), e, status);
    }
}
