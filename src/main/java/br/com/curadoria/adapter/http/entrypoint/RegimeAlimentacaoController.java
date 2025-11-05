package br.com.curadoria.adapter.http.entrypoint;

import br.com.curadoria.adapter.http.dto.RegimeAlimentacaoDTO;
import br.com.curadoria.adapter.http.dto.response.RestResult;
import br.com.curadoria.core.services.RegimeAlimentacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/regime")
@AllArgsConstructor
public class RegimeAlimentacaoController {
    private final RegimeAlimentacaoService serviceRegimeAlimentacao;

    @GetMapping("/regimes_alimentacao")
    public ResponseEntity<RestResult<List<RegimeAlimentacaoDTO>>> getRegimeAlimentacao() {
        List<RegimeAlimentacaoDTO> dto = serviceRegimeAlimentacao.getRegimeAlimentacao();
        return ResponseEntity.ok(new RestResult<>(dto));
    }
}
