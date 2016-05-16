package com.github.hualuomoli.base.entity;

import java.util.Date;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityColumnType;

// 公共属性
public class CommonField {

	@EntityColumn(name = "id", type = EntityColumnType.STRING, comment = "主键", length = 32)
	private String id;
	@EntityColumn(name = "version", comment = "数据版本号")
	private Integer version;
	@EntityColumn(name = "create_by", type = EntityColumnType.STRING, comment = "创建人", length = 32)
	private String createBy;
	@EntityColumn(name = "create_date", type = EntityColumnType.TIMESTAMP, comment = "创建时间")
	private Date createDate;
	@EntityColumn(name = "update_by", type = EntityColumnType.STRING, comment = "修改人", length = 32)
	private String updateBy;
	@EntityColumn(name = "update_date", type = EntityColumnType.TIMESTAMP, comment = "修改时间")
	private Date updateDate;
	@EntityColumn(name = "status", comment = "数据状态")
	private Integer status;

	public CommonField() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

}
