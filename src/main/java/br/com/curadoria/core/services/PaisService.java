package br.com.curadoria.core.services;

import br.com.curadoria.adapter.http.dto.PaisDTO;
import br.com.curadoria.core.entities.Estado;
import br.com.curadoria.core.entities.Pais;
import br.com.curadoria.core.ports.repositories.PaisRepository;
import br.com.curadoria.core.services.mapper.PaisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaisService {
    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private PaisMapper mapper;

    public List<PaisDTO> getPaises(String expand) {
        if("possui_hotel".equals(expand))
            return mapper.mapAll(paisRepository.findPaisesComHoteis());
        else
            return mapper.mapAll(paisRepository.findPaisComMunicipio()
                .stream()
                .sorted(Comparator.comparing(Pais::getNome))
                .collect(Collectors.toList()));
    }
}
