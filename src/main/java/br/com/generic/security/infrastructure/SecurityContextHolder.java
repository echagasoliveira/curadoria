package br.com.generic.security.infrastructure;

import br.com.generic.security.enums.TokenProvider;
import br.com.generic.security.model.AccessTokenInfo;
import com.amazonaws.services.lightsail.model.UnauthenticatedException;

import java.io.Serializable;
import java.util.Optional;

public class SecurityContextHolder {
    private static InheritableThreadLocal<SecurityRequest> threadLocal = new InheritableThreadLocal<>();

    public static <T> Optional<T> get(final Class<T> clazz){
        Object securityContext = threadLocal.get();
        return clazz.isInstance(securityContext) ? Optional.ofNullable(clazz.cast(securityContext)) : Optional.empty();
    }

    public static SecurityContext getSecurityContext(){
        Optional<SecurityContext> optional = get(SecurityContext.class);
        return (SecurityContext) optional.orElseThrow(() -> {
            return new UnauthenticatedException("The security context dows not existi");
        });
    }

    public static AccessTokenInfo getAccessTokenInfo() { return getSecurityContext().getAccessTokenInfo();}

    public static String getToken() { return getSecurityContext().getToken();}

    public static TokenProvider getTokenProvider(){ return getSecurityContext().getTokenProvider();}

    public static <T extends SecurityContext> void set(final T securityRequest) {
        threadLocal.set((SecurityRequest) securityRequest);
    }

    public static void remove() { threadLocal.remove();}

    private SecurityContextHolder(){
    }

    public interface SecurityContext extends Serializable {
        String getToken();

        AccessTokenInfo getAccessTokenInfo();

        TokenProvider getTokenProvider();
    }
}
