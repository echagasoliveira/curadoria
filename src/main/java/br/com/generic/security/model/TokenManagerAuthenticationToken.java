package br.com.generic.security.model;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;

@Getter
public class TokenManagerAuthenticationToken extends AbstractAuthenticationToken
{
    private static final long serialVersionUID = 540L;
    private String token;
    private UserDetails credentials;

    public TokenManagerAuthenticationToken(final String token){
        super(new HashSet());
        this.setAuthenticated(true);
        this.token = token;
        this.credentials = new User(token, token, new HashSet());
    }

    public UserDetails getPrincipal(){ return this.credentials;}
}
