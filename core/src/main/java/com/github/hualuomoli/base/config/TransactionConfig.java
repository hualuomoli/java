package com.github.hualuomoli.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启用注解事务管理，使用CGLib代理
 * @author hualuomoli
 *
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class TransactionConfig {

}
