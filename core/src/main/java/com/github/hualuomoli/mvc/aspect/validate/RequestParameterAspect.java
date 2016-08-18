package com.github.hualuomoli.mvc.aspect.validate;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.github.hualuomoli.mvc.validator.EntityValidator;
import com.github.hualuomoli.mvc.validator.EntityValidatorUtils;

// 请求参数合法性
@Aspect
@Component(value = "com.github.hualuomoli.mvc.aspect.validate.RequestParameterAspect")
public class RequestParameterAspect {

	private static final Class<?> validatorClass = EntityValidator.class;

	// 切点到controller
	@Pointcut("execution(* com.github.hualuomoli..*.*Controller.*(..))")
	public void pointcut() {
	}

	@Before("pointcut()")
	public void doBefore(JoinPoint joinPoint) {

		Object[] args = joinPoint.getArgs();
		if (args == null || args.length == 0) {
			return;
		}

		for (int i = 0; i < args.length; i++) {
			Object obj = args[i];
			if (obj == null) {
				continue;
			}
			if (validatorClass.isAssignableFrom(obj.getClass())) {
				EntityValidatorUtils.valid((EntityValidator) obj);
			}

		}
	}

}
