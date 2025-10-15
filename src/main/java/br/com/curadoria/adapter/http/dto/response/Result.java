package br.com.curadoria.adapter.http.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected List<Message> messages;
}
