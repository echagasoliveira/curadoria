package br.com.generic.security.enums;

import javax.annotation.processing.Generated;
import java.util.*;

public enum TokenProvider {
    INTERNAL("INT"),
    EXTERNAL("EXT");
    
    private static final Map<String, TokenProvider> map = new HashMap();
    
    private String value;
    
    private TokenProvider(final String value) { this.value = value;}
    
    public static TokenProvider entryOf(final String value){
        return Objects.nonNull(value) ? (TokenProvider) map.getOrDefault(value.toUpperCase(), null) : null;
    }
    
    @Generated({})
    public String getValue(){return this.value;}
    
    static {
        Iterator var0 = EnumSet.allOf(TokenProvider.class).iterator();
        while(var0.hasNext()){
            TokenProvider tokenProvider = (TokenProvider) var0.next();
            map.put(tokenProvider.getValue(), tokenProvider);
        }
    }
}
