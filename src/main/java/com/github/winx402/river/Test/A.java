package com.github.winx402.river.Test;

import javax.security.auth.Subject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author didi
 * @create 2018-01-08.
 */
public interface A {
    void doIt();

    void doItAgain();
}

class AImpl implements A{

    public void doIt() {
        System.out.println("I am a impl class");
    }

    public void doItAgain(){
        System.out.println("I am a impl class 2");
    }
}

class HWInvocationHandler implements InvocationHandler {

    //目标对象
    private Object target;

    public HWInvocationHandler(Object target){
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("------插入前置通知代码-------------");
        //执行相应的目标方法
        Object rs = method.invoke(target,args);
        System.out.println("------插入后置处理代码-------------");
        return rs;
    }
}

class Main{
    public static void main(String[] args)
    {
        //代理的真实对象
        A a = new AImpl();

        /**
         * InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发
         * 其内部通常包含指向委托类实例的引用，用于真正执行分派转发过来的方法调用.
         * 即：要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法
         */
        InvocationHandler handler = new HWInvocationHandler(a);

        ClassLoader loader = a.getClass().getClassLoader();
        Class[] interfaces = a.getClass().getInterfaces();
        /**
         * 该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例
         */
        A proxy = (A) Proxy.newProxyInstance(loader, interfaces, handler);

        System.out.println("动态代理对象的类型："+proxy.getClass().getName());

        proxy.doIt();

        proxy.doItAgain();
    }
}


