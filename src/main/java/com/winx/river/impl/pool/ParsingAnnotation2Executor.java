package com.winx.river.impl.pool;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

/**
 * @author wangwenxiang
 * @create 2017-05-26.
 */
public interface ParsingAnnotation2Executor<T extends Annotation> {

    String group(T annotation);

    ExecutorService parsing(T annotation);

}
