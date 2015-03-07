package reflect;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import reflect.impl.BookFacadeImpl;

import java.lang.reflect.Method;

public class CglibBookFacadeProxy implements MethodInterceptor {
    private Object target;

    /**
     * 创建代理对象
     *
     * @param target
     * @return
     */
    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("execute callback.intercept() begin");
        proxy.invokeSuper(obj, args);
        System.out.println("execute callback.intercept() end");
        return null;
    }

    public static void main(String[] args) {
        CglibBookFacadeProxy proxy=new CglibBookFacadeProxy();
        BookFacadeImpl bookProxy=(BookFacadeImpl)proxy.getInstance(new BookFacadeImpl());
        bookProxy.addBook();
    }
}
