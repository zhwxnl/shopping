package com.woniuxy.model;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
//ʵ����ע����
public @interface TypeAnno {
	String value();//��¼��Ӧ�����ݿ����
}
