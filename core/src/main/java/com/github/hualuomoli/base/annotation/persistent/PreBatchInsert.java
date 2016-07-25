package com.github.hualuomoli.base.annotation.persistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 批量插入前持久化预处理
 * @author hualuomoli
 *
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PreBatchInsert {

}
