package br.com.curadoria.config.customgrant;

import br.com.curadoria.core.ports.projections.UserDetailsProjection;
import br.com.curadoria.core.ports.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2RefreshTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2RefreshTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.security.Principal;
import java.util.Date;
import java.util.List;

public class CustomRefreshTokenAuthenticationProvider
        implements AuthenticationProvider {

    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2RefreshTokenAuthenticationProvider delegate;
    private UserRepository repository;

    public CustomRefreshTokenAuthenticationProvider(
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
            UserRepository repository) {

        this.authorizationService = authorizationService;
        this.repository = repository;
        this.delegate = new OAuth2RefreshTokenAuthenticationProvider(
                authorizationService,
                tokenGenerator
        );
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        OAuth2RefreshTokenAuthenticationToken refreshAuth =
                (OAuth2RefreshTokenAuthenticationToken) authentication;

        OAuth2Authorization authorization =
                authorizationService.findByToken(
                        refreshAuth.getRefreshToken(),
                        OAuth2TokenType.REFRESH_TOKEN
                );

        if (authorization == null) {
            throw new OAuth2AuthenticationException(
                    OAuth2ErrorCodes.INVALID_GRANT
            );
        }

        return delegate.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2RefreshTokenAuthenticationToken.class
                .isAssignableFrom(authentication);
    }
}
