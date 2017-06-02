package com.winx.impl.pool;

import com.google.common.base.Strings;

import javax.swing.plaf.PanelUI;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author wangwenxiang
 * @create 2017-05-27.
 */
public enum ReturnType {
    UNKNOW, VOID, CARRIER, OTHER;

    private static final String CARRIER_TYPE = "com.winx.impl.pool.Carrier";

    private static final String VOID_TYPE = "void";

    public static ReturnType parsingReturnType(Method method) {
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType == null) {
            return UNKNOW;
        }
        return subClassName(genericReturnType.toString());
    }

    private static ReturnType subClassName(String s) {
        if (Strings.isNullOrEmpty(s)) return UNKNOW;
        if (VOID_TYPE.equalsIgnoreCase(s)) return VOID;
        if (s.startsWith(CARRIER_TYPE)) return CARRIER;
        return OTHER;
    }

    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException {
        Class<ReturnType> returnTypeClass = ReturnType.class;
        System.out.println(parsingReturnType(returnTypeClass.getMethod("get", Method.class)));
    }
}
