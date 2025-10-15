package br.com.generic.security.model;

import br.com.generic.security.exceptions.ValidateTokenException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJwtParser;
import org.springframework.util.Assert;

import javax.annotation.processing.Generated;
import java.io.Serializable;

public class AccessTokenInfo implements Serializable {
    private static final long serialVersionUID = -7595672326004586823L;

    @JsonProperty("audience")
    private String audience;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("expires_in")
    private long expiresIn = Long.MIN_VALUE;

    @JsonProperty("jwt_token")
    @JsonDeserialize(
            as = DefaultClaims.class
    )
    private transient Claims claims;

    public AccessTokenInfo(){}

    public AccessTokenInfo(final String token) throws ValidateTokenException {
        try{
            String[] splitToken = token.split("\\.");
            Assert.isTrue(splitToken.length  == 3, "Token de formato inválido.");
            String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
            DefaultJwtParser parser = new DefaultJwtParser();
            Jwt<?, ?> jwt = parser.parse(unsignedToken);
            this.claims = (Claims) Claims.class.cast(jwt.getBody());
        }
        catch(Exception var6){
            throw new ValidateTokenException(var6.getMessage(), var6);
        }
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("AccessTokenInfo [audience=");
        builder.append(this.audience);
        builder.append(", userId=");
        builder.append(this.userId);
        builder.append(", scope=");
        builder.append(this.scope);
        builder.append(", expiresIn=");
        builder.append(this.expiresIn);
        builder.append(", claims=");
        builder.append(this.claims);
        builder.append("]");

        return builder.toString();
    }

    @Generated({})
    public String getAudience() { return this.audience; }

    @Generated({})
    public String getUserId() { return this.userId; }

    @Generated({})
    public String getScope() { return this.scope; }

    @Generated({})
    public long getExpiresIn() { return this.expiresIn; }

    @Generated({})
    public Claims getClaims() { return this.claims; }
}
