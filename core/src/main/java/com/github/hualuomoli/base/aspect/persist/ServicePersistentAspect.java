package com.github.hualuomoli.base.aspect.persist;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
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

import com.github.hualuomoli.base.BasePersistent;
import com.github.hualuomoli.base.CommonPersistent;
import com.github.hualuomoli.base.annotation.persistent.PreBatchInsert;
import com.github.hualuomoli.base.annotation.persistent.PreDelete;
import com.github.hualuomoli.base.annotation.persistent.PreInsert;
import com.github.hualuomoli.base.annotation.persistent.PreUpdate;
import com.github.hualuomoli.base.constant.Status;
import com.github.hualuomoli.commons.util.RandomUtils;
import com.github.hualuomoli.login.service.LoginUserService;

// service 持久化 预处理
@Aspect
@Component(value = "com.github.hualuomoli.base.aspect.persist.ServicePretreatmentAspect")
public class ServicePersistentAspect {

	private static final Logger logger = LoggerFactory.getLogger(ServicePersistentAspect.class);

	@Autowired
	private LoginUserService loginUserService;

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

			// 预处理
			args = this.prePersistent(joinPoint, args, method);
			ret = joinPoint.proceed(args);
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
	@SuppressWarnings("rawtypes")
	private Object[] prePersistent(JoinPoint joinPoint, Object[] args, Method method) {

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

				if (annotation.annotationType() == PreInsert.class && BasePersistent.class.isAssignableFrom(parameter.getClass())) {
					// 插入前预处理
					args[i] = this._doPreInsert((BasePersistent) parameter);
					break;
				} else if (annotation.annotationType() == PreUpdate.class && CommonPersistent.class.isAssignableFrom(parameter.getClass())) {
					// 修改前预处理
					args[i] = this._doPreUpdate((CommonPersistent) parameter);
					break;
				} else if (annotation.annotationType() == PreDelete.class && CommonPersistent.class.isAssignableFrom(parameter.getClass())) {
					// 逻辑删除前预处理
					args[i] = this._doPreDelete((CommonPersistent) parameter);
					break;
				} else if (annotation.annotationType() == PreBatchInsert.class && Collection.class.isAssignableFrom(parameter.getClass())) {
					// 批量插入前持久化预处理
					args[i] = this._doPreBatchInsert((Collection) parameter);
					break;
				}
			}

		} // end for

		return args;

	}

	/**
	 * 插入前预处理
	 */
	private Object _doPreInsert(BasePersistent parameter) {
		if (logger.isDebugEnabled()) {
			logger.debug("插入前预处理");
		}

		// id
		if (StringUtils.isBlank(parameter.getId())) {
			parameter.setId(RandomUtils.getUUID());
		}

		// common
		if (parameter instanceof CommonPersistent) {
			String username = loginUserService.getUsername();
			Date currentDate = loginUserService.getCurrentDate();

			CommonPersistent commonPersistent = (CommonPersistent) parameter;
			commonPersistent.setCreateBy(username);
			commonPersistent.setCreateDate(currentDate);
			commonPersistent.setUpdateBy(username);
			commonPersistent.setUpdateDate(currentDate);
			commonPersistent.setStatus(Status.NOMAL.getValue());
		}

		return parameter;

	}

	/**
	 * 修改前预处理
	 */
	private Object _doPreUpdate(CommonPersistent parameter) {
		if (logger.isDebugEnabled()) {
			logger.debug("修改前预处理");
		}

		// common
		String username = loginUserService.getUsername();
		Date currentDate = loginUserService.getCurrentDate();

		parameter.setCreateBy(null);
		parameter.setCreateDate(null);
		parameter.setUpdateBy(username);
		parameter.setUpdateDate(currentDate);
		// parameter.setStatus(Status.NOMAL.getValue());// 只有正常数据才可以修改

		return parameter;

	}

	/**
	 * 删除前预处理
	 */
	private Object _doPreDelete(CommonPersistent parameter) {
		if (logger.isDebugEnabled()) {
			logger.debug("删除前预处理");
		}

		// common
		String username = loginUserService.getUsername();
		Date currentDate = loginUserService.getCurrentDate();

		CommonPersistent deleteObject;
		try {
			deleteObject = parameter.getClass().newInstance();
			deleteObject.setId(parameter.getId());
		} catch (Exception e) {
			throw new RuntimeException();
		}

		deleteObject.setUpdateBy(username);
		deleteObject.setUpdateDate(currentDate);
		deleteObject.setStatus(Status.DELETED.getValue()); // 设置为删除状态
		return deleteObject;

	}

	/**
	 * 批量插入前预处理
	 */
	@SuppressWarnings({ "rawtypes" })
	private Object _doPreBatchInsert(Collection parameter) {

		if (parameter.size() == 0) {
			return parameter;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("批量插入前预处理");
		}

		String username = loginUserService.getUsername();
		Date currentDate = loginUserService.getCurrentDate();

		for (Object object : parameter) {
			if (object instanceof BasePersistent) {
				BasePersistent basePersistent = (BasePersistent) object;
				if (StringUtils.isBlank(basePersistent.getId())) {
					basePersistent.setId(RandomUtils.getUUID());
				}
				// common
				if (object instanceof CommonPersistent) {
					CommonPersistent commonPersistent = (CommonPersistent) object;
					commonPersistent.setCreateBy(username);
					commonPersistent.setCreateDate(currentDate);
					commonPersistent.setUpdateBy(username);
					commonPersistent.setUpdateDate(currentDate);
					commonPersistent.setStatus(Status.NOMAL.getValue());
				}
			}
		}

		return parameter;

	}

}
