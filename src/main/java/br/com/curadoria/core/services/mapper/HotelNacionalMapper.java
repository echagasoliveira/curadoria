package br.com.curadoria.core.services.mapper;

import br.com.curadoria.adapter.http.dto.HotelNacionalDTO;
import br.com.curadoria.core.entities.HotelNacional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface HotelNacionalMapper {

    HotelNacional map(HotelNacionalDTO dto);

    @Mappings({
            @Mapping(source = "municipio.nome", target = "nomeMunicipio"),
            @Mapping(source = "municipio.id", target = "idMunicipio"),
            @Mapping(source = "municipio.estado.id", target = "idEstado"),
            @Mapping(source = "municipio.estado.uf", target = "uf"),
            @Mapping(source = "regimesAlimentacao", target = "regimesAlimentacaoDTO"),
            @Mapping(source = "palavrasChaves", target = "palavrasChaveDTO")
    })
    HotelNacionalDTO mapToDTO(HotelNacional estado);

    List<HotelNacionalDTO> mapAll(List<HotelNacional> entity);
}

