package br.com.curadoria.core.ports.repositories;

import br.com.curadoria.core.entities.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaisRepository extends JpaRepository<Pais, Integer> {
    List<Pais> findAll();

    @Query(nativeQuery = true, value = """
            SELECT tb_pais.id_continente, tb_pais.id, tb_pais.sigla2, tb_pais.sigla3, tb_pais.nome
                    FROM tb_pais
                    WHERE exists (select 1
                            from tb_municipio
                            where tb_pais.id = tb_municipio.id_pais)
                        and tb_pais.id <> 76
                     ORDER BY tb_pais.nome   
               """)
    List<Pais> findPaisComMunicipio();

    @Query(nativeQuery = true, value = """
            SELECT tb_pais.id_continente, tb_pais.id, tb_pais.sigla2, tb_pais.sigla3, tb_pais.nome
                    FROM tb_pais
                    WHERE exists (select 1
                        from tb_hotel_internacional, tb_municipio
                        where tb_hotel_internacional.id_municipio = tb_municipio.id
                        and tb_municipio.id_pais = tb_pais.id)
                     ORDER BY tb_pais.nome   
               """)
    List<Pais> findPaisesComHoteis();
}
