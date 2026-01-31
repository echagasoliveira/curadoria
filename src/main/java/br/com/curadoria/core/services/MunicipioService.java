package br.com.curadoria.core.services;

import br.com.curadoria.adapter.http.dto.MunicipioDTO;
import br.com.curadoria.core.entities.Estado;
import br.com.curadoria.core.entities.HotelNacional;
import br.com.curadoria.core.entities.Municipio;
import br.com.curadoria.core.entities.Pais;
import br.com.curadoria.core.ports.repositories.EstadoRepository;
import br.com.curadoria.core.ports.repositories.MunicipioRepository;
import br.com.curadoria.core.ports.repositories.PaisRepository;
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
    private PaisRepository paisRepository;

    @Autowired
    private EstadoRepository estadoRepository;

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

    public void postMunicipioInternacional(MunicipioDTO municipioDTO){
        Optional<Pais> optionalPais = paisRepository.findById(municipioDTO.getIdPais());

        if(!optionalPais.isPresent())
            throw new RuntimeException("País não encontrado. Favor tentar novamente mais tarde.");

        Municipio municipio = Municipio.builder()
                .pais(optionalPais.get())
                .nome(municipioDTO.getNome())
                .capital(municipioDTO.getCapital())
                .build();
        municipioRepository.save(municipio);
    }

    public void postMunicipioNacional(MunicipioDTO municipioDTO){
        Optional<Estado> optionalEstado = estadoRepository.findById(Long.valueOf(municipioDTO.getIdEstado()));
        Optional<Pais> optionalPais = paisRepository.findById(MunicipioService.ID_BRASIL);

        if(!optionalEstado.isPresent() || !optionalPais.isPresent())
            throw new RuntimeException("País ou estado não encontrado. Favor tentar novamente mais tarde.");

        Municipio municipio = Municipio.builder()
                .pais(optionalPais.get())
                .estado(optionalEstado.get())
                .nome(municipioDTO.getNome())
                .capital(false)
                .build();
        municipioRepository.save(municipio);
    }

    public void putMunicipioNacional(MunicipioDTO municipioDTO){
        Optional<Estado> optionalEstado = estadoRepository.findById(Long.valueOf(municipioDTO.getIdEstado()));
        Optional<Pais> optionalPais = paisRepository.findById(MunicipioService.ID_BRASIL);

        if(!optionalEstado.isPresent() || !optionalPais.isPresent())
            throw new RuntimeException("País ou estado não encontrado. Favor tentar novamente mais tarde.");

        Municipio municipio = municipioRepository.findById(municipioDTO.getId())
                .orElseThrow(() -> new RuntimeException("Município não encontrado. Favor tentar novamente mais tarde."));

        municipio.setPais(optionalPais.get());
        municipio.setEstado(optionalEstado.get());
        municipio.setNome(municipioDTO.getNome());
        municipio.setCapital(municipioDTO.getCapital());

        municipioRepository.save(municipio);
    }

    public void putMunicipioInternacional(MunicipioDTO municipioDTO){
        Optional<Pais> optionalPais = paisRepository.findById(municipioDTO.getIdPais());

        if(!optionalPais.isPresent())
            throw new RuntimeException("País não encontrado. Favor tentar novamente mais tarde.");

        Municipio municipio = municipioRepository.findById(municipioDTO.getId())
                .orElseThrow(() -> new RuntimeException("Município não encontrado. Favor tentar novamente mais tarde."));

        municipio.setPais(optionalPais.get());
        municipio.setNome(municipioDTO.getNome());
        municipio.setCapital(municipioDTO.getCapital());

        municipioRepository.save(municipio);
    }
}
