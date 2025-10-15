package br.com.curadoria.core.services;

import br.com.curadoria.adapter.http.dto.PalavraChaveDTO;
import br.com.curadoria.core.entities.PalavraChave;
import br.com.curadoria.core.ports.repositories.PalavraChaveRepository;
import br.com.curadoria.core.services.mapper.PaisMapper;
import br.com.curadoria.core.services.mapper.PalavraChaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PalavraChaveService {
    @Autowired
    private PalavraChaveRepository palavraChaveRepository;

    @Autowired
    private PalavraChaveMapper mapper;

    public List<PalavraChaveDTO> getPalavraChaves() {
        return mapper.mapAll(palavraChaveRepository.findAll());
    }
}
