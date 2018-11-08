package com.bingor.forminputview;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IntDef;


/**
 * Created by HXB on 2018/11/7.
 */
@Documented // 表示开启Doc文档
@IntDef({
        InputType.INPUTTYPE_NONE,
        InputType.INPUTTYPE_NUMBER,
        InputType.INPUTTYPE_TEXT,
        InputType.INPUTTYPE_PHONE,
        InputType.INPUTTYPE_NUMBERPASSWORD,
        InputType.INPUTTYPE_TEXTPASSWORD,
        InputType.INPUTTYPE_LISTSELECTE
})
@Target({
        ElementType.PARAMETER,
        ElementType.FIELD,
        ElementType.METHOD,
}) //表示注解作用范围，参数注解，成员注解，方法注解
@Retention(RetentionPolicy.SOURCE) //表示注解所存活的时间,在运行时,而不会存在 .class 文件中
public @interface InputType {
    int INPUTTYPE_NONE = 0;
    int INPUTTYPE_NUMBER = 1;
    int INPUTTYPE_TEXT = 2;
    int INPUTTYPE_PHONE = 3;
    int INPUTTYPE_NUMBERPASSWORD = 4;
    int INPUTTYPE_TEXTPASSWORD = 5;
    int INPUTTYPE_LISTSELECTE = 6;
}
