package com.github.winx402.river.spring;

import com.github.winx402.river.base.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author didi
 */
public class SpringPostProcessor implements BeanPostProcessor{
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Object objectProxy = ProxyFactory.getObjectProxy(o);
        if (o != objectProxy){
            BeanUtil.copyProperties(o, objectProxy);
        }
        return objectProxy;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}