package com.github.hualuomoli.base.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.hualuomoli.base.Persistent;
import com.github.hualuomoli.base.annotation.persistent.PrePersistent;
import com.github.hualuomoli.base.annotation.persistent.Type;
import com.github.hualuomoli.base.constant.Status;
import com.github.hualuomoli.commons.util.RandomUtils;
import com.github.hualuomoli.user.UserUtils;

// service 持久化 预处理
@Aspect
@Component(value = "com.github.hualuomoli.base.aspect.ServicePretreatmentAspect")
public class ServicePersistentAspect {

	private static final Logger logger = LoggerFactory.getLogger(ServicePersistentAspect.class);

	// 切点到controller
	@Pointcut("execution(* com.github.hualuomoli..*.*Service*.*(..))")
	public void pointcut() {
	}

	// show parameter
	@Before("pointcut()")
	public void doBefore(JoinPoint joinPoint) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		Object[] args = joinPoint.getArgs();
		if (args == null || args.length == 0) {
			return;
		}

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		// 预处理
		this.prePersistent(joinPoint, args, method);

	}

	/**
	 * 持久化预处理
	 * @param joinPoint 切点
	 * @param args 参数
	 * @param method 方法
	 */
	@SuppressWarnings("unchecked")
	private void prePersistent(JoinPoint joinPoint, Object[] args, Method method) {

		// 获取方法的注解
		PrePersistent prePersistent = method.getAnnotation(PrePersistent.class);

		if (prePersistent != null) {
			// 类型
			Type type = prePersistent.type();
			switch (type) {
			case INSERT:
				for (Object parameter : args) {
					if (Persistent.class.isAssignableFrom(parameter.getClass())) {
						this._doPreInsert((Persistent) parameter);
					}
				}
				break;
			case UPDATE:
				for (Object parameter : args) {
					if (Persistent.class.isAssignableFrom(parameter.getClass())) {
						this._doPreUpdate((Persistent) parameter);
					}
				}
				break;
			case BATCH_INSERT:
				for (Object parameter : args) {
					// 批量插入,参数为集合
					if (Collection.class.isAssignableFrom(parameter.getClass())) {
						try {
							this._doPreBatchInsert((Collection<Persistent>) parameter);
						} catch (Exception e) {
							logger.warn("{}", e);
						}
					}
				}
				break;
			default:
				logger.warn("please set persistent type.");
			}

		} else {
			// 参数注解,判断每个参数的注解
			Annotation[][] as = method.getParameterAnnotations();
			for (int i = 0; i < as.length; i++) {
				// 参数
				Object parameter = args[i];
				// 注解
				Annotation[] annotations = as[i];

				// 判断注解是否存在
				if (annotations == null || annotations.length == 0) {
					continue;
				}

				// PreInsert注解
				for (Annotation annotation : annotations) {

					if (annotation.annotationType() != PrePersistent.class) {
						continue;
					}

					Type type = ((PrePersistent) annotation).type();
					switch (type) {
					case INSERT:
						if (Persistent.class.isAssignableFrom(parameter.getClass())) {
							this._doPreInsert((Persistent) parameter);
						}
						break;
					case UPDATE:
						if (Persistent.class.isAssignableFrom(parameter.getClass())) {
							this._doPreUpdate((Persistent) parameter);
						}
						break;
					case BATCH_INSERT:
						// 批量插入,参数为集合
						if (Collection.class.isAssignableFrom(parameter.getClass())) {
							try {
								this._doPreBatchInsert((Collection<Persistent>) parameter);
							} catch (Exception e) {
								logger.warn("{}", e);
							}
						}
						break;
					default:
						logger.warn("please set persistent type.");
					}
					// end switch
				} // end for
			} // end for
		} // end else
	}

	/**
	 * 插入前预处理
	 * @param persistent
	 * @return 
	 */
	private void _doPreInsert(Persistent persistent) {
		if (logger.isDebugEnabled()) {
			logger.debug("插入前预处理");
		}
		String username = UserUtils.getUsername();
		Date currentDate = new Date();

		persistent.setId(StringUtils.isBlank(persistent.getId()) ? RandomUtils.getString() : persistent.getId());
		persistent.setCreateBy(username);
		persistent.setCreateDate(currentDate);
		persistent.setUpdateBy(username);
		persistent.setUpdateDate(currentDate);
		persistent.setStatus(Status.NOMAL.getValue());

	}

	/**
	 * 修改前预处理
	 * @param persistent
	 * @return 
	 */
	private void _doPreUpdate(Persistent persistent) {
		if (logger.isDebugEnabled()) {
			logger.debug("修改前预处理");
		}
		String username = UserUtils.getUsername();
		Date currentDate = new Date();

		persistent.setUpdateBy(username);
		persistent.setUpdateDate(currentDate);

	}

	/**
	 * 批量插入前预处理
	 * @param persistent
	 * @return 
	 */
	private void _doPreBatchInsert(Collection<Persistent> persistents) {
		if (logger.isDebugEnabled()) {
			logger.debug("批量插入前预处理");
		}
		String username = UserUtils.getUsername();
		Date currentDate = new Date();

		for (Persistent persistent : persistents) {
			persistent.setId(StringUtils.isBlank(persistent.getId()) ? RandomUtils.getString() : persistent.getId());
			persistent.setCreateBy(username);
			persistent.setCreateDate(currentDate);
			persistent.setUpdateBy(username);
			persistent.setUpdateDate(currentDate);
			persistent.setStatus(Status.NOMAL.getValue());
		}

	}

}
