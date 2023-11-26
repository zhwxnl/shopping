package com.woniuxy.model;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(CONSTRUCTOR)
//满参构造
public @interface ConsAnno {
	int value();//记录列数
}
