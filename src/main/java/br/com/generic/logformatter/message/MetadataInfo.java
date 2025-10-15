package br.com.generic.logformatter.message;

import ch.qos.logback.classic.spi.LoggerContextVO;

import java.util.Map;

public class MetadataInfo {
    private Object info;
    private Map<String, String> mdcPropertyMap;
    private LoggerContextVO loggerContextVO;

    public Object info() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public Map<String, String> mdcPropertyMap() {
        return mdcPropertyMap;
    }

    public void setMdcPropertyMap(Map<String, String> mdcPropertyMap) {
        this.mdcPropertyMap = mdcPropertyMap;
    }

    public LoggerContextVO loggerContextVO() {
        return loggerContextVO;
    }

    public void setLoggerContextVO(LoggerContextVO loggerContextVO) {
        this.loggerContextVO = loggerContextVO;
    }
}
