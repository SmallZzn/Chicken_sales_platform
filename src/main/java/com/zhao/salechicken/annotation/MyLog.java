package com.zhao.salechicken.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Log 注解打印，可以标记在类或者方法上
 * <p>
 * ElementType.TYPE：可以在类、接口、枚举声明
 * ElementType.METHOD：可以在方法声明
 * Retention注解括号中的"RetentionPolicy.RUNTIME"意思是让 MyLog 这个注解的生命周期一直程序运行时都存在
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLog {
    /**
     * 模块标题
     */
    String title() default "";

    /**
     * 日志内容
     */
    String content() default "";
}