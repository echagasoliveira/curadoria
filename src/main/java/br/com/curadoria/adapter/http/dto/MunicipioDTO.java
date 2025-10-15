package br.com.curadoria.adapter.http.dto;

import br.com.curadoria.core.entities.Estado;
import br.com.curadoria.core.entities.Pais;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MunicipioDTO {
    @JsonProperty("id")
    @Range(min = 1, message="id.invalido")
    private Integer id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("id_pais")
    private Integer idPais;

    @JsonProperty("id_estado")
    private Integer idEstado;
}
