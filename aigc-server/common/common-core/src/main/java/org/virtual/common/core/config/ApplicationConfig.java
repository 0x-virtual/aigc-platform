package org.virtual.common.core.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 程序注解配置
 */
@AutoConfiguration
@EnableAspectJAutoProxy
@EnableAsync(proxyTargetClass = true)
public class ApplicationConfig {

}
