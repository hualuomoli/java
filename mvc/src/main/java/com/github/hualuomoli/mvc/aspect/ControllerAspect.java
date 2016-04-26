package com.github.hualuomoli.mvc.aspect;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;

/**
 * controller切点
 * @author hualuomoli
 *
 */
@Aspect
@Component(value = "com.github.hualuomoli.mvc.aspect.ControllerAspect")
public class ControllerAspect {

	// 本地异常日志记录对象
	private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

	// 切点到controller
	@Pointcut("execution(* com.github.hualuomoli..*.*Controller.*(..))")
	public void pointcut() {
	}

	// show parameter
	@Before("pointcut()")
	public void doBefore(JoinPoint joinPoint) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(joinPoint.getSignature().getDeclaringTypeName());
		buffer.append(".");
		buffer.append(joinPoint.getSignature().getName());

		logger.debug("call {}", buffer.toString());
		logger.debug("{}", joinPoint);

		List<Object> parameters = this.getValidParameter(joinPoint);
		for (Object parameter : parameters) {
			logger.debug("parameter {}", ToStringBuilder.reflectionToString(parameter));
		}

	}

	/**
	 * 获取有效的参数
	 * @param joinPoint JoinPoint
	 * @return 有效参数集合
	 */
	private List<Object> getValidParameter(JoinPoint joinPoint) {
		return this.getValidParameter(joinPoint, new Validator() {

			@Override
			public boolean valid(Object parameter) {

				if (parameter instanceof HttpServletRequest) {
					return false;
				}
				if (parameter instanceof HttpServletResponse) {
					return false;
				}
				if (parameter instanceof Model) {
					return false;
				}

				return true;
			}

		});
	}

	/**
	 * 获取有效的参数
	 * @param joinPoint JoinPoint
	 * @param validator 校验器
	 * @return 有效参数集合
	 */
	private List<Object> getValidParameter(JoinPoint joinPoint, Validator validator) {
		List<Object> list = Lists.newArrayList();

		if (joinPoint.getArgs() == null || joinPoint.getArgs().length == 0) {
			return list;
		}

		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			Object obj = args[i];

			if (validator.valid(obj)) {
				list.add(obj);
			}

		}

		return list;
	}

	// 有效性
	interface Validator {

		boolean valid(Object parameter);

	}

	// 处理并返回
	@AfterReturning(pointcut = "pointcut()", returning = "ret")
	public void doAfterReturning(JoinPoint joinPoint, Object ret) {
		logger.debug("return {}", ret);
	}

	@AfterThrowing(pointcut = "pointcut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
		logger.debug("exception {}", e);
		throw e;
	}

}
