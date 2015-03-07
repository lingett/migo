package spring.bean;

import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Data
@Component("beanExample")
public class BeanExample implements InitializingBean, DisposableBean {
    private int id;
    private String name;

    public BeanExample() {
        System.out.println("--------||　executing BeanExample.BeanExample()...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("--------||　executing BeanExample.destroy()...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("--------||　executing BeanExample.afterPropertiesSet()...");
    }
}
