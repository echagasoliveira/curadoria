package br.com.curadoria.adapter.http.entrypoint;

import br.com.curadoria.adapter.http.dto.EstadoDTO;
import br.com.curadoria.adapter.http.dto.HotelNacionalRequestDTO;
import br.com.curadoria.adapter.http.dto.MunicipioDTO;
import br.com.curadoria.adapter.http.dto.PaisDTO;
import br.com.curadoria.adapter.http.dto.response.RestResult;
import br.com.curadoria.core.services.EstadoService;
import br.com.curadoria.core.services.MunicipioService;
import br.com.curadoria.core.services.PaisService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/localidade")
@AllArgsConstructor
public class LocalidadeController {
    private final PaisService servicePais;
    private final EstadoService serviceEstado;
    private final MunicipioService serviceMunicipio;

    @GetMapping("/paises")
    public ResponseEntity<RestResult<List<PaisDTO>>> getPaises(@RequestParam(name = "expand", required = false) String expand) {
        List<PaisDTO> dto = servicePais.getPaises(expand);
        return ResponseEntity.ok(new RestResult<>(dto));
    }

    @GetMapping("/municipios")
    public ResponseEntity<RestResult<List<MunicipioDTO>>> getMunicipios(@RequestParam(name = "id_pais", required = false) Integer idPais,
                                                                        @RequestParam(name = "id_estado", required = false) Integer idEstado,
                                                                        @RequestParam(name = "expand", required = false) String expand) {
        List<MunicipioDTO> dto = serviceMunicipio.getMunicipios(idPais, idEstado, expand);
        return ResponseEntity.ok(new RestResult<>(dto));
    }

    @PostMapping("/municipio_internacional")
    public ResponseEntity<Void> postMunicipioInternacional(@RequestBody @Valid MunicipioDTO municipioDTO) {
        serviceMunicipio.postMunicipioInternacional(municipioDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/municipio_internacional")
    public ResponseEntity<Void> putMunicipioInternacional(@RequestBody @Valid MunicipioDTO municipioDTO) {
        serviceMunicipio.putMunicipioInternacional(municipioDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/estados")
    public ResponseEntity<RestResult<List<EstadoDTO>>> getEstados(@RequestParam(name = "expand", required = false) String expand) {
        List<EstadoDTO> dto = serviceEstado.getEstados(expand);
        return ResponseEntity.ok(new RestResult<>(dto));
    }
}
