package br.com.curadoria.core.services;

import br.com.curadoria.adapter.http.dto.MunicipioDTO;
import br.com.curadoria.core.entities.Municipio;
import br.com.curadoria.core.ports.repositories.MunicipioRepository;
import br.com.curadoria.core.services.mapper.MunicipioMapper;
import br.com.curadoria.core.services.mapper.PaisMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MunicipioService {
    public static final Integer ID_BRASIL = 76;
    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private MunicipioMapper mapper;

    public List<MunicipioDTO> getMunicipios(Integer idPais, Integer idEstado, String expand) {
        if(idPais == null && idEstado == null)
            return mapper.mapAll(municipioRepository.findAll());
        if(idEstado == null) {
            if("possui_hotel_internacional".equals(expand))
                return mapper.mapAll(municipioRepository.findByPaisIdComHotel(idPais));
            else
                return mapper.mapAll(municipioRepository.findByPaisId(idPais));
        }
        else {
            if("possui_hotel_nacional".equals(expand))
                return mapper.mapAll(municipioRepository.findByEstadoIdComHotel(idEstado));
            else
                return mapper.mapAll(municipioRepository.findByEstadoId(idEstado));
        }
    }
}
