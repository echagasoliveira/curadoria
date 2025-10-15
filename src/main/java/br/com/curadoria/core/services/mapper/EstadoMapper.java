package br.com.curadoria.core.services.mapper;

import br.com.curadoria.adapter.http.dto.EstadoDTO;
import br.com.curadoria.adapter.http.dto.MunicipioDTO;
import br.com.curadoria.core.entities.Estado;
import br.com.curadoria.core.entities.Municipio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EstadoMapper {

    Estado map(EstadoDTO dto);

    EstadoDTO mapToDTO(Estado estado);

    List<EstadoDTO> mapAll(List<Estado> entity);
}

