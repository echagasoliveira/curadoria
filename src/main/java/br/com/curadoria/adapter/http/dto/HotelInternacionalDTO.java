package br.com.curadoria.adapter.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelInternacionalDTO {
	@JsonProperty("id")
	private String id;

	@JsonProperty("id_municipio")
	private Integer idMunicipio;
	
	@JsonProperty("nome_municipio")
	private String nomeMunicipio;

	@JsonProperty("nome")
	private String nome;

	@JsonProperty("url")
	private String url;

	@JsonProperty("id_pais")
	private Integer idPais;

	@JsonProperty("nome_pais")
	private String nomePais;

	@JsonProperty("nome_continente")
	private String nomeContinente;

	@JsonProperty("regime_alimentacao")
	private List<RegimeAlimentacaoDTO> regimesAlimentacaoDTO;

	@JsonProperty("palavra_chave")
	private String palavrasChaves;
}
