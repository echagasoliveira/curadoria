package br.com.curadoria.adapter.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoDTO {
    @JsonProperty("id")
    @Range(min = 1, message="id.invalido")
    private Integer id;

    @JsonProperty("uf")
    @NotBlank(message="uf.blank")
    private String uf;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("regiao")
    private String regiao;
}
