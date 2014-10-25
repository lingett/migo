package spring.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationEventInstance extends ApplicationEvent {
    private String name;

    public ApplicationEventInstance(Object source, String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String... args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/config/spring/appcontext-core.xml");
        ApplicationEventInstance instanceOfEvent = new ApplicationEventInstance(context, "instance");
        context.publishEvent(instanceOfEvent);
    }
}
