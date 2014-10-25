package utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

public class BeansUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String className) {
        return applicationContext.getBean(className);
    }

    public static <T> T getBean(Class<T> clazz) {
        T result = null;
        Map<String, T> beansMap = applicationContext.getBeansOfType(clazz);
        if(beansMap.size() == 1) {
            result = beansMap.values().iterator().next();
        }
        return result;
    }
}
