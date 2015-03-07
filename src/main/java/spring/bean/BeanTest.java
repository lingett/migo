package spring.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest {
    private static ApplicationContext instance;

    static {
        if (instance == null) instance = buildApplicationContext();
    }

    private BeanTest() {
    }

    public static ApplicationContext buildApplicationContext() {
        return new ClassPathXmlApplicationContext("/config/spring/appcontext-core.xml");
    }

    public static ApplicationContext getApplicationContext() {
        return instance;
    }

    public static void main(String... args) {
        ApplicationContext context = BeanTest.getApplicationContext();
        BeanExample example = (BeanExample) context.getBean("beanExample");
        System.out.println("get beanExample instance");
        System.out.println("end");
    }
}
