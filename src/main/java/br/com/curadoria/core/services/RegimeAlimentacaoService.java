package br.com.curadoria.core.services;

import br.com.curadoria.adapter.http.dto.RegimeAlimentacaoDTO;
import br.com.curadoria.core.entities.RegimeAlimentacao;
import br.com.curadoria.core.ports.repositories.RegimeAlimentacaoRepository;
import br.com.curadoria.core.services.mapper.PaisMapper;
import br.com.curadoria.core.services.mapper.RegimeAlimentacaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RegimeAlimentacaoService {
    @Autowired
    private RegimeAlimentacaoRepository regimeAlimentacaoRepository;

    @Autowired
    private RegimeAlimentacaoMapper mapper;

    public List<RegimeAlimentacaoDTO> getRegimeAlimentacao() {
        return mapper.mapAll(regimeAlimentacaoRepository.findAll());
    }
}
