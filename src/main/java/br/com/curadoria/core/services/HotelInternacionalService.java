package br.com.curadoria.core.services;

import br.com.curadoria.adapter.http.dto.HotelInternacionalDTO;
import br.com.curadoria.adapter.http.dto.HotelInternacionalRequestDTO;
import br.com.curadoria.core.entities.*;
import br.com.curadoria.core.ports.repositories.HotelInternacionalRepository;
import br.com.curadoria.core.ports.repositories.MunicipioRepository;
import br.com.curadoria.core.ports.repositories.PaisRepository;
import br.com.curadoria.core.services.mapper.HotelInternacionalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class HotelInternacionalService {
    @Autowired
    private HotelInternacionalRepository hotelInternacionalRepository;

    @Autowired
    private HotelInternacionalMapper mapper;

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private PaisRepository paisRepository;

    public List<HotelInternacionalDTO> searchHoteisInternacionais(Integer idPais, Integer idMunicipio, Integer idRegimeAlimentacao, String palavraChave){
        List<HotelInternacional> hoteisInternacionais = hotelInternacionalRepository.searchHoteisInternacionais(idPais, idMunicipio, idRegimeAlimentacao, palavraChave);
        return mapper.mapAll(hoteisInternacionais.stream().filter(distinctByKey(HotelInternacional::getId)).collect(Collectors.toList()));
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Transactional
    public void postHotelInternacional(HotelInternacionalRequestDTO hotelInternacionalRequestDTO){
        Municipio municipio = null;
        if(hotelInternacionalRequestDTO.getIdMunicipio() == null){
            Optional<Pais> pais = paisRepository.findById(hotelInternacionalRequestDTO.getIdPais());
            if (!pais.isPresent())
                throw new RuntimeException("País não existente. Favor tentar novamente mais tarde.");

            municipio = Municipio.builder()
                    .pais(pais.get())
                    .nome(hotelInternacionalRequestDTO.getNomeMunicipio())
                    .build();
            municipio = municipioRepository.save(municipio);
        } else
            municipio = municipioRepository.findById(hotelInternacionalRequestDTO.getIdMunicipio())
                    .orElseThrow(() -> new RuntimeException("Município não existente. Favor tentar novamente mais tarde."));

        List<RegimeAlimentacao> regimes = hotelInternacionalRequestDTO.getIdsRegimeAlimentacao() == null ?
                new ArrayList<>() :
                hotelInternacionalRequestDTO.getIdsRegimeAlimentacao().stream()
                        .map(id -> RegimeAlimentacao.builder().id(id).build())
                        .collect(Collectors.toList());

        /*List<PalavraChave> palavras = hotelInternacionalRequestDTO.getIdsPalavraChave() == null ?
                new ArrayList<>() :
                hotelInternacionalRequestDTO.getIdsPalavraChave().stream()
                        .map(id -> PalavraChave.builder().id(id).build())
                        .collect(Collectors.toList());*/

        HotelInternacional hotelInternacional = HotelInternacional.builder()
                .municipio(municipio)
                .regimesAlimentacao(regimes)
                .palavrasChaves(hotelInternacionalRequestDTO.getPalavrasChave())
                .nome(hotelInternacionalRequestDTO.getNome())
                .url(hotelInternacionalRequestDTO.getUrl())
                .ativo(true)
                .build();
        hotelInternacionalRepository.save(hotelInternacional);
    }

    @Transactional
    public void putHotelInternacional(HotelInternacionalRequestDTO hotelInternacionalRequestDTO){
        Municipio municipio = null;
        if(hotelInternacionalRequestDTO.getIdMunicipio() == null){
            Optional<Pais> pais = paisRepository.findById(hotelInternacionalRequestDTO.getIdPais());
            if (!pais.isPresent())
                throw new RuntimeException("País não existente. Favor tentar novamente mais tarde.");

            municipio = Municipio.builder()
                    .pais(pais.get())
                    .nome(hotelInternacionalRequestDTO.getNomeMunicipio())
                    .build();
            municipio = municipioRepository.save(municipio);
        } else
            municipio = municipioRepository.findById(hotelInternacionalRequestDTO.getIdMunicipio())
                    .orElseThrow(() -> new RuntimeException("Município não existente. Favor tentar novamente mais tarde."));

        List<RegimeAlimentacao> regimes = hotelInternacionalRequestDTO.getIdsRegimeAlimentacao() == null ?
                new ArrayList<>() :
                hotelInternacionalRequestDTO.getIdsRegimeAlimentacao().stream()
                        .map(id -> RegimeAlimentacao.builder().id(id).build())
                        .collect(Collectors.toList());

        /*List<PalavraChave> palavras = hotelInternacionalRequestDTO.getIdsPalavraChave() == null ?
                new ArrayList<>() :
                hotelInternacionalRequestDTO.getIdsPalavraChave().stream()
                        .map(id -> PalavraChave.builder().id(id).build())
                        .collect(Collectors.toList());*/


        HotelInternacional hotelInternacional = hotelInternacionalRepository.findById(hotelInternacionalRequestDTO.getId())
                .orElseThrow(() -> new RuntimeException("Hotel nacional não encontrado. Favor tentar novamente mais tarde."));

        hotelInternacional.setMunicipio(municipio);
        hotelInternacional.setRegimesAlimentacao(regimes);
        hotelInternacional.setPalavrasChaves(hotelInternacionalRequestDTO.getPalavrasChave());
        hotelInternacional.setNome(hotelInternacionalRequestDTO.getNome());
        hotelInternacional.setAtivo(hotelInternacionalRequestDTO.getAtivo());

        hotelInternacionalRepository.save(hotelInternacional);
    }
}
