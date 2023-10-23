package com.wit.xzy.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//该注解用在方法上
@Retention(RetentionPolicy.RUNTIME)//该注解在程序运行是有效
public @interface LoginRequired {

}
