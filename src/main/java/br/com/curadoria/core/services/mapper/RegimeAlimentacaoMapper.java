package br.com.curadoria.core.services.mapper;
import br.com.curadoria.adapter.http.dto.RegimeAlimentacaoDTO;
import br.com.curadoria.core.entities.RegimeAlimentacao;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RegimeAlimentacaoMapper {

    RegimeAlimentacao map(RegimeAlimentacaoDTO dto);

    RegimeAlimentacaoDTO mapToDTO(RegimeAlimentacao entity);

    List<RegimeAlimentacaoDTO> mapAll(List<RegimeAlimentacao> entity);
}

