package br.com.generic.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class TokenValidationFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenValidationFilter.class);

    private final String authServiceUrl; // URL do seu microserviço de autenticação

    public TokenValidationFilter(String authServiceUrl) {
        this.authServiceUrl = authServiceUrl;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String token = extractTokenFromRequest((HttpServletRequest) request);

        if(desabilitarFiltroValidacao((HttpServletRequest) request))

            if (token != null) {
                boolean authentication = validateToken(token);

                if (!authentication) {
                    configuraSemAutorizacao((HttpServletResponse) response);
                    return; // Bloqueia a requisição sem chamar o filterChain
                } else {
                    // System.out.println("Token valido");
                    LOGGER.info("Token válido!");
                }
            }
            else{
                configuraSemAutorizacao((HttpServletResponse) response);
                return; // Bloqueia a requisição sem chamar o filterChain
            }

        filterChain.doFilter(request, response);
    }

    private static boolean desabilitarFiltroValidacao(HttpServletRequest request) {
        return !request.getRequestURI().contains("/notification/verificacao-de-email-convidado")
                && !request.getRequestURI().contains("/notification/verificacao-de-email")
                && !request.getRequestURI().contains("/error");
    }

    private static void configuraSemAutorizacao(HttpServletResponse response) {
        LOGGER.info("Token inválido!");
        HttpServletResponse httpResponse = response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        // Lógica para extrair o token do cabeçalho Authorization, se estiver presente
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7); // Remove "Bearer " prefix to get just the token
        }

        // Se o token não estiver no cabeçalho Authorization, você pode procurar em
        // cookies ou em outros cabeçalhos

        return null; // Retorna null se o token não estiver presente
    }

    private boolean validateToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        // Faça uma chamada para o microserviço de autenticação para validar o token
        // Use o 'authServiceUrl' e o token
        // Retorne a autenticação com base na resposta do microserviço

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Substitua "authServiceUrl" pela URL real do seu serviço de autenticação
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    authServiceUrl + "/validate",
                    HttpMethod.POST,
                    entity,
                    String.class);

            if (response.getStatusCode().is2xxSuccessful() && "Token is valid".equals(response.getBody())) {
                return true;
            }
        } catch (HttpClientErrorException e) {
            // Capturar o erro do cliente HTTP, se ocorrer
            // Você pode lidar com o erro aqui ou relançá-lo, se necessário
            LOGGER.error(e.getMessage());
        }

        return false; // Retorna null se a validação falhar ou se a resposta não for bem-sucedida
    }

}
