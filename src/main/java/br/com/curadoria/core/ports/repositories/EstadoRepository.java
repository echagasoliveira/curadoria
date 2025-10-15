package br.com.curadoria.core.ports.repositories;

import br.com.curadoria.core.entities.Estado;
import br.com.curadoria.core.entities.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
    List<Estado> findAll();

    @Query(nativeQuery = true, value = """
            SELECT tb_estado.id, tb_estado.uf, tb_estado.nome, tb_estado.latitude, tb_estado.longitude, tb_estado.regiao
                                FROM tb_estado
                                WHERE exists (select 1
            						from tb_hotel_nacional, tb_municipio
            						where tb_hotel_nacional.id_municipio = tb_municipio.id
            						and tb_municipio.id_estado = tb_estado.id)
            					ORDER BY tb_estado.nome
               """)
    List<Estado> findEstadosComHoteis();
}
