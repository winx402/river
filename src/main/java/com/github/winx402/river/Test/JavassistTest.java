package com.github.winx402.river.Test;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @author didi
 * @create 2017-12-20.
 */
public class JavassistTest {

    static Test test;

    public static void main(String[] args) throws Exception {
        ClassPool aDefault = ClassPool.getDefault();
        aDefault.insertClassPath(new ClassClassPath(JavassistTest.class));
        CtClass ctClass = aDefault.get("com.github.winx402.river.Test.Test");
        CtMethod get = ctClass.getDeclaredMethod("get");
        get.insertBefore("System.out.println(\"s\");");
//        Class<Test> testClass = Test.class;
        Class aClass = ctClass.toClass();
        Test o = (Test)aClass.newInstance();
        o.get("a");
        Test test = new Test();
        test.get("b");
        System.out.println(o.getClass());
        System.out.println(test.getClass());
    }

}
