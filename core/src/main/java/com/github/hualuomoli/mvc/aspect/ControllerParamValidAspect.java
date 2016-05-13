package com.github.hualuomoli.mvc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.hualuomoli.mvc.validator.EntityValidator;
import com.github.hualuomoli.mvc.validator.EntityValidatorUtils;

// controller 参数合法性
@Aspect
@Component(value = "com.github.hualuomoli.mvc.aspect.ControllerParamValidAspect")
public class ControllerParamValidAspect {

	private static final Logger logger = LoggerFactory.getLogger(ControllerParamValidAspect.class);
	private static final Class<?> validatorClass = EntityValidator.class;

	// 切点到controller
	@Pointcut("execution(* com.github.hualuomoli..*.*Controller.*(..))")
	public void pointcut() {
	}

	@Before("pointcut()")
	public void doBefore(JoinPoint joinPoint) {
		if (!logger.isDebugEnabled()) {
			return;
		}

		Object[] args = joinPoint.getArgs();
		if (args == null || args.length == 0) {
			return;
		}

		for (int i = 0; i < args.length; i++) {
			Object obj = args[i];

			if (validatorClass.isAssignableFrom(obj.getClass())) {
				EntityValidatorUtils.valid((EntityValidator) obj);
			}

		}
	}

}
