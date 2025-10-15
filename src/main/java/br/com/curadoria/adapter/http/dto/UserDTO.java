package br.com.curadoria.adapter.http.dto;

import br.com.curadoria.adapter.http.entrypoint.validators.Email;
import br.com.curadoria.adapter.http.entrypoint.validators.SenhaForte;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("id")
	private String id;

	private List<String> roles = new ArrayList<>();

	@SenhaForte
	@JsonProperty("senha")
	private String senha;

	@Email
	@JsonProperty("email")
	private String email;
}
