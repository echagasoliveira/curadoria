package br.com.generic.security.exceptions;

public class ValidateTokenException extends Exception{
    private static final long serialVersionUID = 1142831711364715L;
    private static final String CODE = "1060";
    public ValidateTokenException(final String message){super(message);};
    public ValidateTokenException(final Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }
    public ValidateTokenException(final String message, final Throwable throwable){
        super(message, throwable);
    }
    public String getCode() {return "1060";}
}
