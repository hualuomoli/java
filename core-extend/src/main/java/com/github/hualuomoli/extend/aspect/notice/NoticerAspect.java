package com.github.hualuomoli.extend.aspect.notice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.hualuomoli.extend.annotation.persistent.BasicDataNotice;
import com.github.hualuomoli.extend.notice.Noticer;
import com.github.hualuomoli.extend.notice.service.NoticeService;

// service 持久化 预处理
@Aspect
@Component(value = "com.github.hualuomoli.extend.aspect.notice.NoticerAspect")
public class NoticerAspect {

	private static final Logger logger = LoggerFactory.getLogger(NoticerAspect.class);

	@Autowired
	private NoticeService noticeService;

	// 切点到service
	@Pointcut("execution(* com.github.hualuomoli..*.*Service*.*(..))")
	public void pointcut() {
	}

	@Around(value = "pointcut()", argNames = "args")
	public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {

		Object ret = null;

		// 参数
		Object[] args = joinPoint.getArgs();

		if (args != null && args.length > 0) {

			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();

			ret = joinPoint.proceed(args);
			// 预处理
			this.notice(joinPoint, args, method);
		} else {
			ret = joinPoint.proceed(args);
		}

		return ret;

	}

	/**
	 * 持久化预处理
	 * @param joinPoint 切点
	 * @param args 参数
	 * @param method 方法
	 */
	private void notice(JoinPoint joinPoint, Object[] args, Method method) {

		// 参数注解,判断每个参数的注解
		Annotation[][] as = method.getParameterAnnotations();
		for (int i = 0; i < as.length; i++) {
			// 参数
			Object parameter = args[i];
			// 参数为空
			if (parameter == null) {
				continue;
			}

			// 注解
			Annotation[] annotations = as[i];

			// 注解不存在
			if (annotations == null || annotations.length == 0) {
				continue;
			}

			// 注解处理参数
			for (Annotation annotation : annotations) {

				if (annotation.annotationType() == BasicDataNotice.class) {
					// 插入前预处理
					this.doNotice(parameter);
					break;
				}
			}

		} // end for

	}

	// 通知
	private Object doNotice(Object parameter) {
		if (logger.isDebugEnabled()) {
			logger.debug("基础数据通知业务数据");
		}
		if (parameter instanceof Noticer) {
			Noticer noticer = (Noticer) parameter;
			noticeService.notice(noticer);
		}
		return parameter;
	}

}
