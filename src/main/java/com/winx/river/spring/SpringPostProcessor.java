package com.winx.river.spring;

import com.winx.river.base.ProxyFactory;
import com.winx.river.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author didi
 * @create 2017-09-27.
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