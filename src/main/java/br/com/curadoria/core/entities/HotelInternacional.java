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
@Table(name = "tb_hotel_internacional")
public class HotelInternacional implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_internacional_seq")
    @SequenceGenerator(name = "hotel_internacional_seq", sequenceName = "tb_hotel_internacional_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    @Column(unique = true)
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_hotel_internacional_regime",
            joinColumns = @JoinColumn(name = "id_hotel_internacional"),
            inverseJoinColumns = @JoinColumn(name = "id_regime_alimentacao"))
    private List<RegimeAlimentacao> regimesAlimentacao = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_hotel_internacional_palavra_chave",
            joinColumns = @JoinColumn(name = "id_hotel_internacional"),
            inverseJoinColumns = @JoinColumn(name = "id_palavra_chave"))
    private List<PalavraChave> palavrasChaves = new ArrayList<>();

    @Column(name = "url")
    private String url;

    @Column(name = "ativo")
    private Boolean ativo;
}
