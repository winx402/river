package com.winx.river.spring;

import com.winx.river.base.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author didi
 * @create 2017-09-27.
 */
public class SpringPostProcessor implements BeanPostProcessor{
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return ProxyFactory.getObjectProxy(o);
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}