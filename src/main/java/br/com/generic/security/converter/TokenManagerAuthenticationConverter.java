package br.com.generic.security.converter;

import br.com.generic.security.model.TokenManagerAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenManagerAuthenticationConverter implements AuthenticationConverter {
    private static final Pattern PATTERN = Pattern.compile("\\s*([bB][eE][aA][rR][eE][rR])\\s+(.+)\\s*");

    public TokenManagerAuthenticationConverter(){
    }

    public TokenManagerAuthenticationToken convert(final HttpServletRequest request){
        String authorizationContent = request.getHeader("Authorization");
        if(Objects.nonNull(authorizationContent)){
            Matcher matcher = PATTERN.matcher(authorizationContent);
            if(!matcher.matches())
                throw new BadCredentialsException("Authorization bearer doesn't have two parts. (ex. 'Bearer token')");
            else
            {
                String token = matcher.group(2);
                return new TokenManagerAuthenticationToken(token);
            }
        }
        else {
            return null;
        }
    }
}
