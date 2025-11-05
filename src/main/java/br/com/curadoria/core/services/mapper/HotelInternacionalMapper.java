package br.com.curadoria.core.services.mapper;

import br.com.curadoria.adapter.http.dto.HotelInternacionalDTO;
import br.com.curadoria.core.entities.HotelInternacional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface HotelInternacionalMapper {

    HotelInternacional map(HotelInternacionalDTO dto);

    @Mappings({
            @Mapping(source = "municipio.id", target = "idMunicipio"),
            @Mapping(source = "municipio.nome", target = "nomeMunicipio"),
            @Mapping(source = "municipio.pais.id", target = "idPais"),
            @Mapping(source = "municipio.pais.nome", target = "nomePais"),
            @Mapping(source = "municipio.pais.continente.nome", target = "nomeContinente"),
            @Mapping(source = "regimesAlimentacao", target = "regimesAlimentacaoDTO"),
            @Mapping(source = "palavrasChaves", target = "palavrasChaves")
    })
    HotelInternacionalDTO mapToDTO(HotelInternacional hotelInternacional);

    List<HotelInternacionalDTO> mapAll(List<HotelInternacional> entity);
}

