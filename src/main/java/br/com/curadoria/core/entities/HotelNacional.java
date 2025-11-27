package br.com.curadoria.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_hotel_nacional")
public class HotelNacional implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    @Column(unique = true)
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_hotel_nacional_regime",
            joinColumns = @JoinColumn(name = "id_hotel_nacional"),
            inverseJoinColumns = @JoinColumn(name = "id_regime_alimentacao"))
    private List<RegimeAlimentacao> regimesAlimentacao = new ArrayList<>();

    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_hotel_nacional_palavra_chave",
            joinColumns = @JoinColumn(name = "id_hotel_nacional"),
            inverseJoinColumns = @JoinColumn(name = "id_palavra_chave"))
    private List<PalavraChave> palavrasChaves = new ArrayList<>();*/

    @Column(name = "palavras_chave")
    private String palavrasChaves;

    @Column(name = "url")
    private String url;

    @Column(name = "ativo")
    private Boolean ativo;
}
