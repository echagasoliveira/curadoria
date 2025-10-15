package br.com.curadoria.adapter.dataprovider.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorizationDTO {
    private String authorization;
    private String xcuradoriaFlowId;
    private String xcuradoriaCorrelationId;
}
