package br.com.generic.logformatter.message;

public class BusinessInfo {
    private String message;
    private String businessKey;
    private String metada;

    public String message() {
        return message;
    }

    public String businessKey() {
        return businessKey;
    }

    public String metada() {
        return metada;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public void setMetada(String metada) {
        this.metada = metada;
    }
}
