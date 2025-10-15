package br.com.curadoria.core.services.mapper;

import br.com.curadoria.adapter.http.dto.PaisDTO;
import br.com.curadoria.core.entities.Pais;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PaisMapper {

    Pais map(PaisDTO dto);

    PaisDTO mapToDTO(Pais entity);

    List<PaisDTO> mapAll(List<Pais> entity);
}

