package br.com.curadoria.core.ports.repositories;

import br.com.curadoria.core.entities.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {
    @Query(nativeQuery = true, value = """
            SELECT tb_municipio.id, tb_municipio.codigo_igbe, tb_municipio.id_pais, tb_municipio.nome, tb_municipio.latitude, tb_municipio.longitude, tb_municipio.capital, tb_municipio.id_estado, tb_municipio.id_siafi, tb_municipio.ddd, tb_municipio.fuso_horario
                    FROM tb_municipio
                    WHERE tb_municipio.id_estado = ?1
               """)
    List<Municipio> findByEstadoId(Integer idEstado);

    @Query(nativeQuery = true, value = """
            SELECT tb_municipio.id, tb_municipio.codigo_igbe, tb_municipio.id_pais, tb_municipio.nome, tb_municipio.latitude, tb_municipio.longitude, tb_municipio.capital, tb_municipio.id_estado, tb_municipio.id_siafi, tb_municipio.ddd, tb_municipio.fuso_horario
                    FROM tb_municipio
                    WHERE exists (select 1
                          	from tb_hotel_nacional
                          	where tb_municipio.id = tb_hotel_nacional.id_municipio)
                          and tb_municipio.id_estado = ?1
               """)
    List<Municipio> findByEstadoIdComHotel(Integer idEstado);

    @Query(nativeQuery = true, value = """
            SELECT tb_municipio.id, tb_municipio.codigo_igbe, tb_municipio.id_pais, tb_municipio.nome, tb_municipio.latitude, tb_municipio.longitude, tb_municipio.capital, tb_municipio.id_estado, tb_municipio.id_siafi, tb_municipio.ddd, tb_municipio.fuso_horario
                    FROM tb_municipio
                    WHERE tb_municipio.id_pais = ?1
               """)
    List<Municipio> findByPaisId(Integer idPais);

    @Query(nativeQuery = true, value = """
            SELECT tb_municipio.id, tb_municipio.codigo_igbe, tb_municipio.id_pais, tb_municipio.nome, tb_municipio.latitude, tb_municipio.longitude, tb_municipio.capital, tb_municipio.id_estado, tb_municipio.id_siafi, tb_municipio.ddd, tb_municipio.fuso_horario
                    FROM tb_municipio
                    WHERE exists (select 1\s
                          	from tb_hotel_internacional
                          	where tb_municipio.id = tb_hotel_internacional.id_municipio)
                    and tb_municipio.id_pais = ?1
               """)
    List<Municipio> findByPaisIdComHotel(Integer idPais);

    List<Municipio> findAll();
}
