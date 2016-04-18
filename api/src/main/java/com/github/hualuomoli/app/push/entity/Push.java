package com.github.hualuomoli.app.push.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * APP 推送信息,默认推送全部
 * @author hualuomoli
 *
 */
public class Push implements Serializable {

	private static final long serialVersionUID = 6058531918740542024L;

	private Set<String> tags; // 标签
	private Set<String> aliases; // 别名
	private Set<String> registrationIds; //

	public Push() {
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public Set<String> getAliases() {
		return aliases;
	}

	public void setAliases(Set<String> aliases) {
		this.aliases = aliases;
	}

	public Set<String> getRegistrationIds() {
		return registrationIds;
	}

	public void setRegistrationIds(Set<String> registrationIds) {
		this.registrationIds = registrationIds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
