package com.github.hualuomoli.demo.aspect;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 切面Demo
 * @author hualuomoli
 *
 */
@Aspect
@Component(value = "com.github.hualuomoli.demo.aspect.DemoAspect")
public class DemoAspect {

	private static final Logger logger = LoggerFactory.getLogger(DemoAspect.class);

	@Before("execution(* com.github.hualuomoli.demo.web.login.LoginController.login(..))")
	public void doMethod(JoinPoint joinPoint) {
		logger.debug("调用了特定类的特定方法");
	}

	@Before("execution(* com.github.hualuomoli.demo.web.login.LoginController.*(..))")
	public void doClass(JoinPoint joinPoint) {
		logger.debug("调用了特定类");
	}

	@Before("execution(* com.github.hualuomoli.demo.web.login.*.*(..))")
	public void doPackage(JoinPoint joinPoint) {
		logger.debug("调用了特定包");
	}

	@Before("execution(* com.github.hualuomoli.demo.web..*.*(..))")
	public void doPackageAndSub(JoinPoint joinPoint) {
		logger.debug("调用了特定包与子包");
	}

	@Before("execution(* com.github.hualuomoli..*Controller.*(..))")
	public void doController(JoinPoint joinPoint) {
		logger.debug("调用了Controller结尾");
	}

	///////////////////////////////
	// 切点到class
	@Pointcut("execution(* com.github.hualuomoli.demo.web.user.UserController.*(..))")
	public void pointcut() {
		logger.debug("定义统一切点");
	}

	// 请求开始
	@Before(value = "pointcut()")
	public void doBefore(JoinPoint joinPoint) {

		logger.debug("before.........");

		// show parameter
		Object[] args = joinPoint.getArgs();
		if (args == null || args.length == 0) {
			return;
		}
		for (int i = 0; i < args.length; i++) {
			logger.debug("param {}", args[i]);
		}
	}

	// 处理并返回
	@AfterReturning(pointcut = "pointcut()", returning = "ret")
	public void doAfterReturning(JoinPoint joinPoint, Object ret) {
		logger.debug("afterReturning.........");
		logger.debug("return value {}", ret);
	}

	// 有异常抛出
	@AfterThrowing(pointcut = "execution(* com.github.hualuomoli.demo.web.login.LoginController.login(..))", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
		logger.debug("afterThrowing.........");
		logger.debug("exception {}", e.getMessage());
		throw e;
	}

	// 环绕通知
	// 参数 ProceedingJoinPoint
	// 返回 Object
	@Around("pointcut()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug("around.............");

		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			logger.debug(ToStringBuilder.reflectionToString(args[i]));
		}

		Object ret = joinPoint.proceed(args);
		logger.debug("return value {}", ret);

		logger.debug("arounded...");

		return ret;
	}

}
