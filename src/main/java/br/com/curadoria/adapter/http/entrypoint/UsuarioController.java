package br.com.curadoria.adapter.http.entrypoint;

import br.com.curadoria.adapter.http.dto.UserDTO;
import br.com.curadoria.adapter.http.dto.PlanoAssinaturaDTO;
import br.com.curadoria.adapter.http.dto.response.RestResult;
import br.com.curadoria.core.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {
    private final UserService service;

    @GetMapping("/loja")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<RestResult<UserDTO>> getLoja() {
        UserDTO dto = service.getClient();
        return ResponseEntity.ok(new RestResult<>(dto));
    }

    @PostMapping(path = "/loja",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> postLoja(@RequestBody @NotEmpty @Valid UserDTO dto) {
        service.postLoja(dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(path = "/efetivar_plano_assinatura",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> postEfetivarPlanoAssinatura(@RequestBody @NotEmpty @Valid PlanoAssinaturaDTO dto) throws JsonProcessingException {
        service.postEfetivarPlanoAssinatura(dto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
