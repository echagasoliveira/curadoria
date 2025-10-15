package br.com.generic.security.infrastructure;


import br.com.generic.security.enums.TokenProvider;
import br.com.generic.security.exceptions.ValidateTokenException;
import br.com.generic.security.model.AccessTokenInfo;

public class SecurityRequest implements SecurityContextHolder.SecurityContext {
    private static final long serialVersionUID = -6115941861530220406L;
    private final String token;
    private final AccessTokenInfo accessTokenInfo;

    public SecurityRequest(final String token) throws ValidateTokenException {
        this.token = token;
        this.accessTokenInfo = new AccessTokenInfo(token);
    }

    public String getToken() {return this.token;}

    public TokenProvider getTokenProvider(){
        return TokenProvider.entryOf((String)this.accessTokenInfo.getClaims().get("source", String.class));
    }

    public AccessTokenInfo getAccessTokenInfo() {return this.accessTokenInfo;}
}
