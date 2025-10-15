package br.com.generic.logformatter.message;

import java.time.LocalDateTime;

public class LogMessage {

    private LocalDateTime timestamp;
    private String level;
    private String correlationId;
    private String applicationName;
    private String applicationVersion;
    private ApplicationInfo applicationInfo;
    private BusinessInfo businessInfo;

    public LocalDateTime timestamp() {
        return timestamp;
    }

    public void setTimestamp() {
        this.timestamp = LocalDateTime.now();
    }

    public String level() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String correlationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String applicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String applicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public ApplicationInfo applicationInfo() {
        return applicationInfo;
    }

    public void setApplicationInfo(ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }

    public BusinessInfo businessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(BusinessInfo businessInfo) {
        this.businessInfo = businessInfo;
    }
}
