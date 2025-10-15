package br.com.curadoria.adapter.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelNacionalRequestDTO {
	@Min(value = 1, message = "O campo id deve ser maior ou igual a 1.")
	@JsonProperty("id")
	private Integer id;

	@NotNull(message = "O campo idMunicipio é obrigatório.")
	@JsonProperty("id_municipio")
	private Integer idMunicipio;

	@NotNull(message = "O campo nome é obrigatório.")
	@JsonProperty("nome")
	private String nome;

	@NotEmpty(message = "A lista de identificadores de regime de alimentação não pode estar vazia.")
	@JsonProperty("ids_regime_alimentacao")
	private List<Integer> idsRegimeAlimentacao;

	@NotEmpty(message = "A lista de identificadores de palavra chave não pode estar vazia.")
	@JsonProperty("ids_palavra_chave")
	private List<Integer> idsPalavraChave;

	@JsonProperty("url")
	private String url;

	@JsonProperty("ativo")
	private Boolean ativo = true;
}
