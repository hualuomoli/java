package com.github.hualuomoli.mvc.aspect.validate;

import java.lang.reflect.Method;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.hualuomoli.exception.AuthException;
import com.github.hualuomoli.login.service.LoginUserService;
import com.github.hualuomoli.mvc.annotation.RequestPermission;
import com.github.hualuomoli.mvc.annotation.RequestRole;
import com.github.hualuomoli.mvc.annotation.RequestToken;

// 请求权限切面
@Aspect
@Component(value = "com.github.hualuomoli.mvc.aspect.validate.RequestSecurityAspect")
public class RequestSecurityAspect {

	private static final Logger logger = LoggerFactory.getLogger(RequestSecurityAspect.class);

	@Autowired
	private LoginUserService loginUserService;

	// 切点到controller
	@Pointcut("execution(* com.github.hualuomoli..*.*Controller.*(..))")
	public void pointcut() {
	}

	@Around(value = "pointcut()", argNames = "args")
	public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		// 获取注解
		this.check(method);

		// 执行
		return joinPoint.proceed(joinPoint.getArgs());

	}

	// 验证安全
	private void check(Method method) {
		this.checkToken(method);
		this.checkRole(method);
		this.checkPermission(method);
	}

	// 登录用户
	private void checkToken(Method method) {
		RequestToken token = method.getAnnotation(RequestToken.class);
		if (token != null) {
			loginUserService.getLoginUsername();
		}
	}

	// 角色
	private void checkRole(Method method) {
		RequestRole requestRole = method.getAnnotation(RequestRole.class);
		if (requestRole != null) {
			String[] values = requestRole.value();
			Set<String> roles = loginUserService.getLoginUserRoles();
			for (String value : values) {
				if (roles.contains(value)) {
					return;
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("用户没有角色 {}", StringUtils.join(values, ","));
			}
			throw new AuthException("用户没有角色" + StringUtils.join(values, ","));
		}
	}

	// 权限
	private void checkPermission(Method method) {
		RequestPermission requestPermission = method.getAnnotation(RequestPermission.class);
		if (requestPermission != null) {
			String permission = requestPermission.value();
			Set<String> permissions = loginUserService.getLoginUserPermissions();
			if (!permissions.contains(permission)) {
				if (logger.isDebugEnabled()) {
					logger.debug("用户没有权限 {}", permission);
				}
				throw new AuthException("用户没有权限" + permission);
			}
		}
	}

}
