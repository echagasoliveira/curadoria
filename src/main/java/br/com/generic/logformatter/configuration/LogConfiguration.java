package br.com.generic.logformatter.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LogConfiguration {
    @Value("${generic.http.correlation-id:null}")
    private String correlationIdKeyName;
    @Value("${generic.application.business-key:null}")
    private String businessKey;
    @Value("${generic.application.name:null}")
    private String ApplicationName;
    @Value("${generic.application.version:null}")
    private String ApplicationVersion;

    public String getCorrelationId() {
        return correlationIdKeyName;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public String getApplicationName() {
        return ApplicationName;
    }

    public String getApplicationVersion() {
        return ApplicationVersion;
    }
}
