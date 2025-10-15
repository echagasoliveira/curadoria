package br.com.curadoria.adapter.dataprovider.security;

import br.com.curadoria.adapter.dataprovider.security.dto.AuthorizationDTO;

public interface AuthorizationProvider {
    AuthorizationDTO getAuth();
}
