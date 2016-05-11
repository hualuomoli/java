package com.github.hualuomoli.base.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.base.Paginator;
import com.github.hualuomoli.base.util.BaseUtils;
import com.github.hualuomoli.commons.constant.Status;

public abstract class BaseEntity extends CommonField implements Paginator {

	private Pagination pagination; // pagination

	public BaseEntity() {
	}

	public void preInsert() {
		String currentUser = BaseUtils.getCurrentUser();
		Date currentDate = BaseUtils.getCurrentDate();

		setCreateBy(currentUser);
		setCreateDate(currentDate);
		setUpdateBy(currentUser);
		setUpdateDate(currentDate);
		setStatus(getStatus() == null ? Status.NOMAL.getValue() : getStatus());
		setVersion(1);
		setId(StringUtils.isEmpty(getId()) ? BaseUtils.getRandomId() : getId());
	}

	public void preUpdate() {
		setUpdateBy(BaseUtils.getCurrentUser());
		setUpdateDate(BaseUtils.getCurrentDate());
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

}
