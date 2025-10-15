package br.com.curadoria.adapter.http.filter;

import br.com.generic.logformatter.configuration.LogConfiguration;
import br.com.generic.security.converter.TokenManagerAuthenticationConverter;
import br.com.generic.security.exceptions.ValidateTokenException;
import br.com.generic.security.infrastructure.SecurityContextHolder;
import br.com.generic.security.infrastructure.SecurityRequest;
import br.com.generic.security.model.TokenManagerAuthenticationToken;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Objects;
import javax.security.sasl.AuthenticationException;

@Component
@Order
@RequiredArgsConstructor
public class MdcFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MdcFilter.class);
    private final LogConfiguration loggerConfiguration;
    private final TokenManagerAuthenticationConverter authenticationConverter = new TokenManagerAuthenticationConverter();

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        trySetCorrelationIdInMDC(request);
        // filterChain.doFilter(servletRequest,servletResponse);
        try {
            if (!desabilitarFiltroValidacao(request)) {
                TokenManagerAuthenticationToken authRequest = this.authenticationConverter.convert(request);
                if (authRequest == null) {
                    LOGGER.trace(
                            "Did not process authentication request since failed to find username and passowrd in Bearer Authorization");
                    filterChain.doFilter(request, servletResponse);
                    return;
                }
                SecurityContextHolder.set(new SecurityRequest(authRequest.getToken()));
            }
            filterChain.doFilter(request, servletResponse);
        } catch (ValidateTokenException | AuthenticationException var6) {
            org.springframework.security.core.context.SecurityContextHolder.clearContext();
            SecurityContextHolder.remove();
            LOGGER.debug("Failed to process authentication request", var6);
            filterChain.doFilter(request, servletResponse);
        }
    }

    private static boolean desabilitarFiltroValidacao(HttpServletRequest request) {
        return request.getRequestURI().contains("/usuario/prospect")
                || request.getRequestURI().contains("/usuario/cliente");
    }

    private void trySetCorrelationIdInMDC(HttpServletRequest request){
        String headerXCorrelationID = request.getHeader(loggerConfiguration.getCorrelationId());
        if(Objects.nonNull(headerXCorrelationID))
            MDC.put(loggerConfiguration.getCorrelationId(),headerXCorrelationID);
    }
}