package br.com.curadoria.core.services;

import br.com.curadoria.core.ports.repositories.EstadoRepository;
import br.com.curadoria.core.services.mapper.EstadoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.curadoria.adapter.http.dto.EstadoDTO;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import br.com.curadoria.core.entities.*;

@Service
public class EstadoService {
    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoMapper mapper;

    public List<EstadoDTO> getEstados(String expand) {
        if("possui_hotel".equals(expand))
            return mapper.mapAll(estadoRepository.findEstadosComHoteis());
        else
            return mapper.mapAll(estadoRepository.findAll()
                    .stream()
                    .sorted(Comparator.comparing(Estado::getNome))
                    .collect(Collectors.toList())
            );
    }
}
