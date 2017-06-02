package com.winx.test.river;

import com.winx.base.ConcreteClassCallbackFilter;
import com.winx.base.FunctionDistribute;
import net.sf.cglib.proxy.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author wangwenxiang
 * @create 2017-05-25.
 */
public class ObjectProxy {

    public static class Test{

        public String test(String name){
            System.out.println("params : " + name);
            return name;
        }
    }

    private static ObjectProxy.Test test;

    public static void main(String[] args) throws NoSuchMethodException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ObjectProxy.Test.class);
        enhancer.setCallbacks(new Callback[]{new Interceptor()});
        enhancer.setCallbackFilter(new ConcreteClassCallbackFilter());
        ObjectProxy.Test test = (ObjectProxy.Test) enhancer.create();
        test.test("hhh");
    }

    public static class Interceptor implements MethodInterceptor {

        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return method.invoke(test, objects);
        }
    }

    public static class ConcreteClassCallbackFilter implements CallbackFilter {

        public int accept(Method method) {
            return 0;
        }
    }
}
