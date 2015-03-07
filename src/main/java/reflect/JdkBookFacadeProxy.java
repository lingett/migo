package reflect;

import reflect.impl.BookFacadeImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkBookFacadeProxy implements InvocationHandler {
    private Object target;

    /**
     * 绑定委托对象并返回一个代理类
     *
     * @param target
     * @return
     */
    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("execute proxy.invoke() begin");
        Object result = method.invoke(target, args);
        System.out.println("execute proxy.invoke() end");
        return result;
    }

    public static void main(String[] args) {
        JdkBookFacadeProxy proxy = new JdkBookFacadeProxy();
        BookFacade bookProxy = (BookFacade) proxy.bind(new BookFacadeImpl());
        bookProxy.addBook();
    }
}
