package com.github.hualuomoli.plugin.roll.entity;

import java.util.Date;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.BaseField;

/**
 * 轮询频度
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
@EntityTable(comment = "轮询频度", unique = { "dataId" })
public class PollFrequency extends BaseField {

	@EntityColumn(comment = "数据")
	private String dataId;
	@EntityColumn(comment = "调度频度 ,使用逗号(,)分割(s=秒,m=分钟,h=小时,d=天)", length = 200, nullable = false)
	private String frequency;
	@EntityColumn(comment = "剩余调度频度", length = 200, nullable = false)
	private String remainFrequency;
	@EntityColumn(comment = "下次执行时间", name = "execute_time", nullable = false)
	private Date executeTime;
	@EntityColumn(comment = "优先级,值越大越优先", name = "priority")
	private Integer priority;
	@EntityColumn(comment = "锁定数据字符串,用于锁定后获取锁定的数据,未锁定使用UNLOCK")
	private String lockString;
	@EntityColumn(comment = "自动解锁时间")
	private Date unLockTime;

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getRemainFrequency() {
		return remainFrequency;
	}

	public void setRemainFrequency(String remainFrequency) {
		this.remainFrequency = remainFrequency;
	}

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getLockString() {
		return lockString;
	}

	public void setLockString(String lockString) {
		this.lockString = lockString;
	}

	public Date getUnLockTime() {
		return unLockTime;
	}

	public void setUnLockTime(Date unLockTime) {
		this.unLockTime = unLockTime;
	}

}
