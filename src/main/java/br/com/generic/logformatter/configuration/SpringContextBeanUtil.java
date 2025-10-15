package br.com.generic.logformatter.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SpringContextBeanUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    public static <T extends Object> T getBean(Class<T> beanClass){
        if(Objects.isNull(context)){
            return  null;
        }else{
            return  context.getBean(beanClass);
        }
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextBeanUtil.context = context;
    }
}
