package spring.application;

import org.apache.tools.ant.util.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

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

    public static void main(String... args) throws Exception {
        String path = "/config/spring/appcontext-core.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(path);
        ApplicationEventInstance instanceOfEvent = new ApplicationEventInstance(context, "instance");
        context.publishEvent(instanceOfEvent);

//        String path = "classpath:config/spring/appcontext-core.xml";
//        DefaultResourceLoader loader = new DefaultResourceLoader();
//        Resource resource = loader.getResource(path);

//        String path = "classpath*:config/spring/appcontext-*.xml";
//        ResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
//        Resource[] resources = loader.getResources(path);

        System.out.println("end");
    }
}
