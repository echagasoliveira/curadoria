package br.com.curadoria.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@SuppressWarnings("serial")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "tb_municipio")
public class Municipio implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Long codigo_igbe;

	@ManyToOne
	@JoinColumn(name = "id_pais")
	private Pais pais;
	private String nome;
	private Double latitude;
	private Double longitude;
	private Boolean capital;

	@ManyToOne
	@JoinColumn(name = "id_estado")
	private Estado estado;
	private String idSiafi;
	private Integer ddd;
	private String fusoHorario;
}
