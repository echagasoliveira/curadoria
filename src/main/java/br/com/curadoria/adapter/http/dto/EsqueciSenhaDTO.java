package br.com.curadoria.adapter.http.dto;

import br.com.curadoria.adapter.http.entrypoint.validators.Email;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EsqueciSenhaDTO {
    @JsonProperty("senha")
    private String senha;

    @JsonProperty("numero_documento")
    @Email
    private String email;
}
