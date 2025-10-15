package br.com.curadoria.adapter.http.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class FieldMessage {
    @JsonProperty("campo")
    private final String field;
    @JsonProperty("valor")
    private final String value;
    @JsonProperty("mensagem")
    private final String message;
}
