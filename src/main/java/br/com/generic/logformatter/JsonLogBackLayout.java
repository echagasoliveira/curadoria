package br.com.generic.logformatter;

import br.com.generic.logformatter.configuration.LogConfiguration;
import br.com.generic.logformatter.configuration.SpringContextBeanUtil;
import br.com.generic.logformatter.message.ApplicationInfo;
import br.com.generic.logformatter.message.BusinessInfo;
import br.com.generic.logformatter.message.LogMessage;
import br.com.generic.logformatter.message.MetadataInfo;
import br.com.generic.logformatter.util.LocalDateTimeAdapter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Objects;


public class JsonLogBackLayout extends LayoutBase<ILoggingEvent> {

	private Gson gson;
	private LogConfiguration logConfiguration;
	private static InetAddress inetAddress;

	static {
		try{
			inetAddress = InetAddress.getLocalHost();
		}catch (UnknownHostException e){
			inetAddress = null;
		}
	}

	public JsonLogBackLayout(){
		gson = new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.create();
	}

	protected LogConfiguration getLogConfiguration() { return SpringContextBeanUtil.getBean(LogConfiguration.class);}

	@Override
	public String doLayout(ILoggingEvent event){
		LogMessage logMessage = new LogMessage();
		logConfiguration =  getLogConfiguration();

		if(Objects.nonNull(event.getLevel())){
			logMessage.setLevel(event.getLevel().toString());
		}
		logMessage.setTimestamp();
		if(Objects.nonNull(logConfiguration)){
			logMessage.setApplicationName(logConfiguration.getApplicationName());
			logMessage.setApplicationVersion(logConfiguration.getApplicationVersion());
			logMessage.setCorrelationId(event.getMDCPropertyMap().getOrDefault(logConfiguration.getCorrelationId(),"00000000-0000-0000-0000-000000000000"));
		}
		logMessage.setBusinessInfo(createBusinessInfo(event,logConfiguration));
		logMessage.setApplicationInfo(createApplicarionInfo(event,logConfiguration));
		StringBuffer result = new StringBuffer();
		result.append(gson.toJson(logMessage));
		result.append(CoreConstants.LINE_SEPARATOR);
		return result.toString();
	}

	private BusinessInfo createBusinessInfo(ILoggingEvent event, LogConfiguration logConfiguration){
		BusinessInfo businessInfo = new BusinessInfo();
		businessInfo.setMessage(event.getMessage());
		if(Objects.nonNull(logConfiguration)){
			businessInfo.setBusinessKey(logConfiguration.getBusinessKey());
		}

		MetadataInfo metadataInfo = new MetadataInfo();
		if(Objects.nonNull(event.getArgumentArray()) && event.getArgumentArray().length > 0){
			metadataInfo.setInfo(event.getArgumentArray()[0]);
		}
		if(Objects.nonNull(event.getLoggerContextVO())){
			metadataInfo.setLoggerContextVO(event.getLoggerContextVO());
		}
		if(Objects.nonNull(event.getMDCPropertyMap())){
			metadataInfo.setMdcPropertyMap(event.getMDCPropertyMap());
		}
		return businessInfo;
	}

	private ApplicationInfo createApplicarionInfo(ILoggingEvent event, LogConfiguration logConfiguration){
		ApplicationInfo applicationInfo = new ApplicationInfo();

		if(Objects.nonNull(inetAddress)){
			applicationInfo.setHostName(inetAddress.getHostName());
			applicationInfo.setMachineIp(inetAddress.getHostAddress());
		}
		applicationInfo.setThreadName();
		applicationInfo.setStacktrace(getException(event));
		return applicationInfo;
	}

	private String getException(ILoggingEvent event){
		if(Objects.nonNull(event.getThrowableProxy())){
			StringBuilder str = new StringBuilder();
			for(StackTraceElementProxy e : event.getThrowableProxy().getStackTraceElementProxyArray()){
				str.append(e.getSTEAsString());
			}
			return str.toString();
		}
		return null;
	}
}
