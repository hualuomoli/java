package com.github.hualuomoli.mvc.aspect.monitor;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.mvc.validator.EntityValidator;

// controller调用日志
@Aspect
@Component(value = "com.github.hualuomoli.mvc.aspect.monitor.ControllerLogAspect")
public class ControllerLogAspect {

	private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);
	private static final Class<?> validatorClass = EntityValidator.class;

	// 切点到controller
	@Pointcut("execution(* com.github.hualuomoli..*.*Controller.*(..))")
	public void pointcut() {
	}

	// show parameter
	@Before("pointcut()")
	public void doBefore(JoinPoint joinPoint) {
		if (!logger.isDebugEnabled()) {
			return;
		}

		Object[] args = joinPoint.getArgs();
		if (args == null || args.length == 0) {
			return;
		}

		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();

		logger.debug("调用的类 {}", className);
		logger.debug("调用的方法 {}", methodName);
		for (int i = 0; i < args.length; i++) {
			Object obj = args[i];
			if (obj == null) {
				continue;
			}
			logger.debug("调用的方法参数{}类型 {}", (i + 1), obj.getClass().getName());
		}

		for (int i = 0; i < args.length; i++) {
			Object obj = args[i];
			if (obj == null) {
				continue;
			}
			// Spring的参数不输出
			if (obj.getClass().getName().startsWith("org.springframework")) {
				continue;
			} else if (obj.getClass().getName().startsWith("org.apache.catalina")) {
				continue;
			} else if (obj.getClass().getName().startsWith("java.lang")) {
				logger.debug("调用的方法参数{}值 = {}", (i + 1), obj);
			} else if (obj.getClass().getName().startsWith("java.util")) {
				logger.debug("调用的方法参数{}值 = {}", (i + 1), obj);
			} else if (validatorClass.isAssignableFrom(obj.getClass())) {
				logger.debug("调用的方法参数{}值 = {}", (i + 1), JsonUtils.getInstance().toJson(obj));
			} else if (StringUtils.startsWith(obj.getClass().getName(), "com.github.hualuomoli")) {
				logger.debug("调用的方法参数{}值 = {}", (i + 1), JsonUtils.getInstance().toJson(obj));
			} else {
				logger.warn("未知类型 {}", obj.getClass().getName());
			}

		}
	}

	// 处理并返回
	@AfterReturning(pointcut = "pointcut()", returning = "ret")
	public void doAfterReturning(JoinPoint joinPoint, Object ret) {
		if (!logger.isDebugEnabled()) {
			return;
		}
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();

		logger.debug("调用的类 {}", className);
		logger.debug("调用的方法 {}", methodName);
		logger.debug("调用的返回值 = {}", ret);
	}

}
