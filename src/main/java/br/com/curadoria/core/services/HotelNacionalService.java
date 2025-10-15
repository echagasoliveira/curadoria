package br.com.curadoria.core.services;

import br.com.curadoria.adapter.http.dto.HotelInternacionalRequestDTO;
import br.com.curadoria.adapter.http.dto.HotelNacionalDTO;
import br.com.curadoria.adapter.http.dto.HotelNacionalRequestDTO;
import br.com.curadoria.core.entities.*;
import br.com.curadoria.core.ports.repositories.HotelInternacionalRepository;
import br.com.curadoria.core.ports.repositories.HotelNacionalRepository;
import br.com.curadoria.core.ports.repositories.MunicipioRepository;
import br.com.curadoria.core.ports.repositories.PaisRepository;
import br.com.curadoria.core.services.mapper.HotelNacionalMapper;
import br.com.curadoria.core.services.mapper.MunicipioMapper;
import com.amazonaws.services.pinpoint.model.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class HotelNacionalService {
    @Autowired
    private HotelNacionalRepository hotelNacionalRepository;

    @Autowired
    private HotelInternacionalRepository hotelInternacionalRepository;

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private HotelNacionalMapper mapper;

    public List<HotelNacionalDTO> searchHoteisNacionais(Integer idEstado, Integer idMunicipio, Integer idRegimeAlimentacao, Integer idPalavraChave) {
        List<HotelNacional> hoteisNacionais = hotelNacionalRepository.searchHoteisNacionais(idEstado, idMunicipio, idRegimeAlimentacao, idPalavraChave);
        return mapper.mapAll(hoteisNacionais.stream().filter(distinctByKey(HotelNacional::getId)).collect(Collectors.toList()));
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public void postHotelNacional(HotelNacionalRequestDTO hotelNacionalRequestDTO){

        Optional<Municipio> municipioOptional = municipioRepository.findById(hotelNacionalRequestDTO.getIdMunicipio());

        if(!municipioOptional.isPresent())
            throw new RuntimeException("Município não encontrado. Favor tentar novamente mais tarde.");

        List<RegimeAlimentacao> regimes = hotelNacionalRequestDTO.getIdsRegimeAlimentacao() == null ?
                new ArrayList<>() :
                hotelNacionalRequestDTO.getIdsRegimeAlimentacao().stream()
                        .map(id -> RegimeAlimentacao.builder().id(id).build())
                        .collect(Collectors.toList());

        List<PalavraChave> palavras = hotelNacionalRequestDTO.getIdsPalavraChave() == null ?
                new ArrayList<>() :
                hotelNacionalRequestDTO.getIdsPalavraChave().stream()
                        .map(id -> PalavraChave.builder().id(id).build())
                        .collect(Collectors.toList());

        HotelNacional hotelNacional = HotelNacional.builder()
                .municipio(municipioOptional.get())
                .regimesAlimentacao(regimes)
                .palavrasChaves(palavras)
                .nome(hotelNacionalRequestDTO.getNome())
                .url(hotelNacionalRequestDTO.getUrl())
                .ativo(true)
                .build();
        hotelNacionalRepository.save(hotelNacional);
    }

    public void putHotelNacional(HotelNacionalRequestDTO hotelNacionalRequestDTO){

        Optional<Municipio> municipioOptional = municipioRepository.findById(hotelNacionalRequestDTO.getIdMunicipio());

        if(!municipioOptional.isPresent())
            throw new RuntimeException("Município não existente. Favor tentar novamente mais tarde.");

        List<RegimeAlimentacao> regimes = hotelNacionalRequestDTO.getIdsRegimeAlimentacao() == null ?
                new ArrayList<>() :
                hotelNacionalRequestDTO.getIdsRegimeAlimentacao().stream()
                        .map(id -> RegimeAlimentacao.builder().id(id).build())
                        .collect(Collectors.toList());

        List<PalavraChave> palavras = hotelNacionalRequestDTO.getIdsPalavraChave() == null ?
                new ArrayList<>() :
                hotelNacionalRequestDTO.getIdsPalavraChave().stream()
                        .map(id -> PalavraChave.builder().id(id).build())
                        .collect(Collectors.toList());

        HotelNacional hotelNacional = hotelNacionalRepository.findById(hotelNacionalRequestDTO.getId())
                .orElseThrow(() -> new RuntimeException("Hotel nacional não encontrado. Favor tentar novamente mais tarde."));

        hotelNacional.setMunicipio(municipioOptional.get());
        hotelNacional.setRegimesAlimentacao(regimes);
        hotelNacional.setPalavrasChaves(palavras);
        hotelNacional.setNome(hotelNacionalRequestDTO.getNome());
        hotelNacional.setAtivo(hotelNacionalRequestDTO.getAtivo());
        hotelNacional.setUrl(hotelNacionalRequestDTO.getUrl());
        hotelNacional.setAtivo(true);
        hotelNacionalRepository.save(hotelNacional);
    }
}
