package br.com.curadoria.core.services.mapper;

import br.com.curadoria.adapter.http.dto.PaisDTO;
import br.com.curadoria.adapter.http.dto.PalavraChaveDTO;
import br.com.curadoria.core.entities.Pais;
import br.com.curadoria.core.entities.PalavraChave;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PalavraChaveMapper {

    PalavraChave map(PalavraChaveDTO dto);

    PalavraChaveDTO mapToDTO(PalavraChave entity);

    List<PalavraChaveDTO> mapAll(List<PalavraChave> entity);
}

