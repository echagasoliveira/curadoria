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
public class PlanoAssinaturaDTO {
    @JsonProperty("user_id")
	private String userId;

	@JsonProperty("apple_user_id")
	private Integer appleUserId;

	@JsonProperty("receipt")
	private String receipt;
}
