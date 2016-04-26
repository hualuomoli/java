package com.github.hualuomoli.demo.mvc.aspect;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 切面Demo
 * @author hualuomoli
 *
 */
@Aspect
@Component
public class DemoAspect {

	// 切点到class
	@Pointcut("execution(* com.github.hualuomoli.web.login.LoginController.*(..))")
	public void someClass() {
	}

	// 切点到package
	@Pointcut("execution(* com.github.hualuomoli.web.login.*.*(..))")
	public void somePackage() {
	}

	// 切点到package和子包
	@Pointcut("execution(* com.github.hualuomoli.web..*.*(..))")
	public void someSubPackage() {
	}

	@Before("someClass()")
	public void doBeforeOnSomeClass(JoinPoint joinPoint) {
		System.out.println("before someClass");
	}

	@Before("somePackage()")
	public void doBeforeOnSomePackage(JoinPoint joinPoint) {
		System.out.println("before somePackage");
	}

	@Before("someSubPackage()")
	public void doBeforeOnSomeSubPackage(JoinPoint joinPoint) {
		System.out.println("before someSubPackage");
	}

	///////////////////////////////
	// 切点到class
	@Pointcut("execution(* com.github.hualuomoli.web.user.UserController.*(..))")
	public void pointcut() {
	}

	// 请求开始
	@Before(value = "pointcut()")
	public void doBefore(JoinPoint joinPoint) {

		System.out.println("before.........");

		// show parameter
		Object[] args = joinPoint.getArgs();
		if (args == null || args.length == 0) {
			return;
		}
		for (int i = 0; i < args.length; i++) {
			System.out.println(ToStringBuilder.reflectionToString(args[i]));
		}
	}

	// 处理并返回
	@AfterReturning(pointcut = "pointcut()", returning = "ret")
	public void doAfterReturning(JoinPoint joinPoint, Object ret) {
		System.out.println("afterReturning.........");
		System.out.println(ret);
	}

	// 有异常抛出
	@AfterThrowing(pointcut = "someClass()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
		System.out.println("afterThrowing.........");
		System.out.println(e);
		throw e;
	}

	// 环绕通知
	// 参数 ProceedingJoinPoint
	// 返回 Object
	@Around("pointcut()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("around.............");

		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			System.out.println(ToStringBuilder.reflectionToString(args[i]));
		}

		Object ret = joinPoint.proceed(args);
		System.out.println(ret);

		System.out.println("arounded...");

		return ret;
	}

}
