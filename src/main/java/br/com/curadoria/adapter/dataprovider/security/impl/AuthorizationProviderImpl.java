package br.com.curadoria.adapter.dataprovider.security.impl;

import br.com.curadoria.adapter.dataprovider.security.AuthorizationProvider;
import br.com.curadoria.adapter.dataprovider.security.dto.AuthorizationDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationProviderImpl implements AuthorizationProvider {

    public AuthorizationDTO getAuth() {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
        String correlationId = null;
        String flowId = null;

        return new AuthorizationDTO("Bearer " + jwtPrincipal.getTokenValue(), flowId, correlationId);
    }
}
