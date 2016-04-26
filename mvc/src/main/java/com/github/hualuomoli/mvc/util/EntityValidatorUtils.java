package com.github.hualuomoli.mvc.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import com.github.hualuomoli.mvc.security.exception.InvalidParameterException;
import com.github.hualuomoli.mvc.valid.EntityValidator;

/**
 * 实体类校验工具
 * @author hualuomoli
 *
 */
public class EntityValidatorUtils {

	private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	/**
	 * 有效性校验
	 * @param entity 被校验的实体类
	 * @return 是否校验成功
	 * @throws InvalidParameterException 校验失败抛出异常
	 */
	public static boolean valid(EntityValidator entity) throws InvalidParameterException {
		return valid(entity, Default.class);
	}

	/**
	 * 有效性校验
	 * @param entity 被校验的实体类
	 * @param groups 校验groups
	 * @return 是否校验成功
	 * @throws InvalidParameterException 校验失败抛出异常
	 */
	public static boolean valid(EntityValidator entity, Class<?>... groups) throws InvalidParameterException {
		Set<ConstraintViolation<EntityValidator>> set = validator.validate(entity, groups);
		if (set == null || set.size() == 0) {
			return true;
		}
		// get exception, split by line
		StringBuilder buffer = new StringBuilder();
		for (ConstraintViolation<EntityValidator> constraintViolation : set) {
			buffer.append(constraintViolation.getMessage());
			buffer.append("\n");
		}
		throw new InvalidParameterException(buffer.substring(0, buffer.length() - 1).toString());
	}

}
