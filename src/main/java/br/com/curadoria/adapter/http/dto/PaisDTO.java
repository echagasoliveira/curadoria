package br.com.curadoria.adapter.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaisDTO {
    @JsonProperty("id")
	private Long id;

	@JsonProperty("nome")
	private String nome;
}
