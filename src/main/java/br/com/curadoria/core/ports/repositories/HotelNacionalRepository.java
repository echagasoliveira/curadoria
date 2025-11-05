package br.com.curadoria.core.ports.repositories;

import br.com.curadoria.core.entities.HotelNacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface HotelNacionalRepository extends JpaRepository<HotelNacional, Integer> {
    @Query(nativeQuery = true, value = """
            SELECT hn.id, hn.id_municipio, hn.nome, hn.url, hn.ativo, hn.palavras_chave
                    	FROM tb_hotel_nacional hn
                    	INNER JOIN tb_municipio m ON hn.id_municipio = m.id
                    	LEFT JOIN tb_hotel_nacional_regime hnr ON hn.id = hnr.id_hotel_nacional
                    	WHERE (m.id_estado = ?1 OR ?1 IS NULL)
                            AND   (m.id = ?2 OR ?2 IS NULL)
                            AND   (hnr.id_regime_alimentacao = ?3 or ?3 is null)
                            AND   (hn.palavras_chave like %?4% or ?4 is null)
                            AND   hn.ativo = true
                   """)
    List<HotelNacional> searchHoteisNacionais(Integer idEstado, Integer idMunicipio, Integer idRegimeAlimentacao, String palavraChave);
}
