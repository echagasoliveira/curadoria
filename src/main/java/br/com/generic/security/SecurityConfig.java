package br.com.generic.security;

import br.com.generic.security.filter.TokenValidationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
@Order(1)
public class SecurityConfig {

    @Value("${auth.service.url}") // Obtenha a URL do microserviço de autenticação do arquivo de propriedades
    private String authServiceUrl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                /*.requireCsrfProtectionMatcher(new RequestMatcher() {
                    private RegexRequestMatcher requestMatcherVerificacaoEmail = new RegexRequestMatcher("/notification/*", null);
                    //private RegexRequestMatcher requestMatcherVerificacaoConvidado = new RegexRequestMatcher("/notification/verificacao-de-email-convidado**", null);

                    @Override
                    public boolean matches(HttpServletRequest request) {
                        return !requestMatcherVerificacaoEmail.matches(request);
                                //&& !requestMatcherVerificacaoConvidado.matches(request);
                    }
                })*/

                .disable() // Desabilita proteção CSRF
                .authorizeRequests()
                .requestMatchers("/**").permitAll() // Permite acesso público a algumas rotas
                //.requestMatchers("/notification/verificacao-de-email").permitAll() // Permite acesso público a algumas rotas

                //.requestMatchers("/notification/verificacao-de-email-convidado").permitAll() // Permite acesso público a algumas rotas
                .anyRequest().authenticated() // Todas as outras rotas exigem autenticação
                .and()
                .addFilterBefore(new TokenValidationFilter(authServiceUrl), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
