package spring.application;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationListenerInstance implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEventInstance) {
            System.out.println("new instance of ApplicationEvent: " + ((ApplicationEventInstance) event).getName());
        }
    }
}
