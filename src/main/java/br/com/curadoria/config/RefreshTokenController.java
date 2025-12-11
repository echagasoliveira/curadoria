/*package br.com.curadoria.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2RefreshTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshTokenController {

    @Autowired
    RegisteredClientRepository registeredClientRepository;

    @Autowired
    OAuth2AuthorizationService authorizationService;

    @Autowired
    OAuth2TokenGenerator<OAuth2Token> tokenGenerator;

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String clientId,
                                     @RequestParam String refreshToken) {

        RegisteredClient client = registeredClientRepository.findByClientId(clientId);
        if (client == null) {
            return ResponseEntity.badRequest().body("Invalid client");
        }

        Authentication clientPrincipal =
                new OAuth2ClientAuthenticationToken(client,
                        ClientAuthenticationMethod.CLIENT_SECRET_BASIC, null);

        OAuth2RefreshTokenAuthenticationToken authToken =
                new OAuth2RefreshTokenAuthenticationToken(
                        refreshToken,
                        clientPrincipal,
                        null,
                        null
                );

        OAuth2Token newToken = tokenGenerator.generate(authToken);
        if (newToken == null) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }

        return ResponseEntity.ok(newToken);
    }
}*/