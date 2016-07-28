package com.github.hualuomoli.mvc.validator;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import com.github.hualuomoli.exception.InvalidEntityException;
import com.google.common.collect.Lists;

/**
 * 实体类有效性
 * @author hualuomoli
 *
 */
public class EntityValidatorUtils {

	private static Validator validator = null;

	static {
		try {
			validator = Validation.buildDefaultValidatorFactory().getValidator();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 有效性校验
	 * @param entity 被校验的实体类
	 * @return 是否校验成功
	 * @throws InvalidException 校验失败抛出异常
	 */
	public static boolean valid(EntityValidator entity) throws InvalidEntityException {
		return valid(entity, Default.class);
	}

	/**
	 * 有效性校验
	 * @param entity 被校验的实体类
	 * @param groups 校验groups
	 * @return 是否校验成功
	 * @throws InvalidException 校验失败抛出异常
	 */
	public static boolean valid(EntityValidator entity, Class<?>... groups) throws InvalidEntityException {
		Set<ConstraintViolation<EntityValidator>> set = validator.validate(entity, groups);
		if (set == null || set.size() == 0) {
			return true;
		}
		List<String> errors = Lists.newArrayList();
		for (ConstraintViolation<EntityValidator> constraintViolation : set) {
			errors.add(constraintViolation.getMessage());
		}
		throw new InvalidEntityException(errors);
	}

}
