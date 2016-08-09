package com.github.hualuomoli.base;

import java.util.Date;

/**
 * 持久化
 * @description 实体类实现该接口,新增和修改时可以预处理
 * @author hualuomoli
 *
 */
public interface CommonPersistent extends BasePersistent {

	// 创建人
	void setCreateBy(String createBy);

	// 创建时间
	void setCreateDate(Date createDate);

	// 修改人
	void setUpdateBy(String updateBy);

	// 修改时间
	void setUpdateDate(Date UpdateDate);

	// 数据状态 #Status
	void setStatus(Integer status);

}