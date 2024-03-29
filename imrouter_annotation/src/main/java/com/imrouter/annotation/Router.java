package com.imrouter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: maodayu
 * Date: 2019/8/30
 * Time: 11:29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Router {
    String path() default "";

    String group() default "";
}
