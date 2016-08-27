package com.github.hualuomoli.base.entity;

import java.util.Date;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityColumnType;

// 公共属性
@SuppressWarnings("serial")
public abstract class CommonField extends BaseField {

	@EntityColumn(name = "create_by", type = EntityColumnType.STRING, comment = "创建人", length = 32, nullable = false)
	private String createBy;
	@EntityColumn(name = "create_date", type = EntityColumnType.TIMESTAMP, comment = "创建时间", nullable = false)
	private Date createDate;
	@EntityColumn(name = "update_by", type = EntityColumnType.STRING, comment = "修改人", length = 32, nullable = false)
	private String updateBy;
	@EntityColumn(name = "update_date", type = EntityColumnType.TIMESTAMP, comment = "修改时间", nullable = false)
	private Date updateDate;
	@EntityColumn(name = "status", comment = "数据状态", nullable = false)
	private Integer status;
	@EntityColumn(name = "status_name", comment = "数据状态名称", nullable = false)
	private String statusName;

	public CommonField() {
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
