package utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void main(String... args) {
        String str = "aabc";

        String regex = ".*a";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            System.out.println("found: " + matcher.group(0));
        }
    }
}
