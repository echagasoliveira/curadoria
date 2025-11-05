package br.com.curadoria.core.ports.repositories;

import br.com.curadoria.core.entities.HotelInternacional;
import br.com.curadoria.core.entities.HotelNacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface HotelInternacionalRepository extends JpaRepository<HotelInternacional, Integer> {
	@Query(nativeQuery = true, value = """
            SELECT hn.id, m.id_pais, hn.id_municipio, hn.nome, hn.url, hn.ativo, hn.palavras_chave
                    	FROM tb_hotel_internacional hn
                    	INNER JOIN tb_municipio m ON hn.id_municipio = m.id
                    	INNER JOIN tb_pais p ON p.id = m.id_pais
                    	INNER JOIN tb_continente c ON c.id = p.id_continente
                    	LEFT JOIN tb_hotel_internacional_regime hnr ON hn.id = hnr.id_hotel_internacional
                    	WHERE (m.id_pais = ?1 OR ?1 IS NULL)
                            AND   (m.id = ?2 OR ?2 IS NULL)
                            AND   (hnr.id_regime_alimentacao = ?3 or ?3 is null)
                            AND   (hn.palavras_chave like %?4% or ?4 is null)
                            AND   hn.ativo = true
                   """)
	List<HotelInternacional> searchHoteisInternacionais(Integer idPais, Integer idMunicipio, Integer idRegimeAlimentacao, String palavraChave);

}
