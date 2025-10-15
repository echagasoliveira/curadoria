package br.com.curadoria.adapter.http.entrypoint;

import br.com.curadoria.adapter.http.dto.HotelInternacionalDTO;
import br.com.curadoria.adapter.http.dto.HotelInternacionalRequestDTO;
import br.com.curadoria.adapter.http.dto.HotelNacionalDTO;
import br.com.curadoria.adapter.http.dto.HotelNacionalRequestDTO;
import br.com.curadoria.adapter.http.dto.response.RestResult;
import br.com.curadoria.core.services.HotelInternacionalService;
import br.com.curadoria.core.services.HotelNacionalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
@AllArgsConstructor
public class HotelController {
    private final HotelNacionalService serviceHotelNacional;
    private final HotelInternacionalService serviceHotelInternacional;

    @GetMapping("/hoteis_nacionais")
    public ResponseEntity<RestResult<List<HotelNacionalDTO>>> getHoteisNacionais(@RequestParam(name = "id_estado", required = false) Integer idEstado,
                                                                                 @RequestParam(name = "id_municipio", required = false) Integer idMunicipio,
                                                                        @RequestParam(name = "id_regime_alimentacao", required = false) Integer idRegimeAlimentacao,
                                                                        @RequestParam(name = "id_palavra_chave", required = false) Integer idPalavraChave) {
        List<HotelNacionalDTO> dto = serviceHotelNacional.searchHoteisNacionais(idEstado, idMunicipio, idRegimeAlimentacao, idPalavraChave);
        return ResponseEntity.ok(new RestResult<>(dto));
    }

    @GetMapping("/hoteis_internacionais")
    public ResponseEntity<RestResult<List<HotelInternacionalDTO>>> getHoteisInternacionais(@RequestParam(name = "id_pais", required = false) Integer idPais,
                                                                                 @RequestParam(name = "id_municipio", required = false) Integer idMunicipio,
                                                                                 @RequestParam(name = "id_regime_alimentacao", required = false) Integer idRegimeAlimentacao,
                                                                                 @RequestParam(name = "id_palavra_chave", required = false) Integer idPalavraChave) {
        List<HotelInternacionalDTO> dto = serviceHotelInternacional.searchHoteisInternacionais(idPais, idMunicipio, idRegimeAlimentacao, idPalavraChave);
        return ResponseEntity.ok(new RestResult<>(dto));
    }

    @PostMapping("/hotel_nacional")
    public ResponseEntity<Void> postHotelNacional(@RequestBody @Valid HotelNacionalRequestDTO hotelNacionalRequestDTO) {
        serviceHotelNacional.postHotelNacional(hotelNacionalRequestDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/hotel_internacional")
    public ResponseEntity<Void> postHotelInternacional(@RequestBody @Valid HotelInternacionalRequestDTO hotelInternacionalRequestDTO) {
        serviceHotelInternacional.postHotelInternacional(hotelInternacionalRequestDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/hotel_nacional")
    public ResponseEntity<Void> putHotelNacional(@RequestBody @Valid HotelNacionalRequestDTO hotelNacionalRequestDTO) {
        serviceHotelNacional.putHotelNacional(hotelNacionalRequestDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/hotel_internacional")
    public ResponseEntity<Void> putHotelInternacional(@RequestBody @Valid HotelInternacionalRequestDTO hotelInternacionalRequestDTO) {
        serviceHotelInternacional.putHotelInternacional(hotelInternacionalRequestDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
