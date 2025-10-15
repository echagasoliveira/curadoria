package br.com.curadoria.core.services.mapper;

import br.com.curadoria.adapter.http.dto.MunicipioDTO;
import br.com.curadoria.adapter.http.dto.PaisDTO;
import br.com.curadoria.core.entities.Municipio;
import br.com.curadoria.core.entities.Pais;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MunicipioMapper {

    Municipio map(MunicipioDTO dto);

    @Mapping(source = "estado.id", target = "idEstado")
    @Mapping(source = "pais.id", target = "idPais")
    MunicipioDTO mapToDTO(Municipio municipio);

    List<MunicipioDTO> mapAll(List<Municipio> entity);
}

