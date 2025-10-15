package br.com.curadoria.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "tb_estado")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String uf;
    @Column(unique = true)
    private String nome;
    private Double latitude;
    private Double longitude;
    private String regiao;

    @OneToMany(mappedBy = "estado", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Municipio> municipios = new HashSet<>();
}
