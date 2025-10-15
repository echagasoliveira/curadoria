package br.com.curadoria.adapter.http.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
@Builder
public class Message {

    @JsonProperty("codigo")
    private final Integer code;
    @JsonProperty("mensagem")
    private final String message;
    @JsonProperty("campos")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<FieldMessage> fieldMessages;
    @JsonProperty("tipo")
    private final MessageType messageType;
}
