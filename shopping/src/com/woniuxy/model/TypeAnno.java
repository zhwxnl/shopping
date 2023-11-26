package com.woniuxy.model;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
//实体类注解类
public @interface TypeAnno {
	String value();//记录对应的数据库表名
}
