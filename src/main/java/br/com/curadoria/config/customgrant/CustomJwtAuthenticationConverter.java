package br.com.curadoria.config.customgrant;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomJwtAuthenticationConverter extends JwtAuthenticationConverter {

    public CustomJwtAuthenticationConverter() {
        // Cria o conversor de authorities
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Personaliza: pega a claim "authorities" do JWT
        authoritiesConverter.setAuthoritiesClaimName("authorities");
        authoritiesConverter.setAuthorityPrefix(""); // se quiser sem "ROLE_" prefix

        // Seta o conversor no JwtAuthenticationConverter
        this.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> granted = authoritiesConverter.convert(jwt);

            // Adiciona lógica extra, por exemplo subscription_status
            String subscriptionStatus = jwt.getClaimAsString("subscription_status");
            if ("expired".equals(subscriptionStatus)) {
                granted.add(new SimpleGrantedAuthority("SUBSCRIPTION_EXPIRED"));
            }

            return granted;
        });
    }
}