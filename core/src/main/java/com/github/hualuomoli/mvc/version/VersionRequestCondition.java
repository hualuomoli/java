package com.github.hualuomoli.mvc.version;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import com.github.hualuomoli.mvc.YamlMvcConfig;

/**
 * 根据版本号请求
 * @author hualuomoli
 *
 */
public class VersionRequestCondition implements RequestCondition<VersionRequestCondition> {

	// 路径中版本的前缀,如v1.0.0.1
	private static final String VERSION_PATTERN = "^[vV]?(\\d+\\.)*\\d+$";
	private static final String VERSION_NAME = YamlMvcConfig.getInstance().getValue("version.name"); // version的名称
	private static final String VERSION_DEFAULT = YamlMvcConfig.getInstance().getValue("version.default"); // 默认版本号

	private String version; // 版本
	private String[] array; // 版本值

	public VersionRequestCondition() {
		this.def();
	}

	public VersionRequestCondition(String version) {
		if (StringUtils.isBlank(version)) {
			this.def();
		} else {
			if (!version.matches(VERSION_PATTERN)) {
				throw new RuntimeException("version must like " + VERSION_PATTERN);
			}
			if (StringUtils.startsWithIgnoreCase(version, "v")) {
				version = version.substring(1);
			}
			this.version = version;
			this.array = StringUtils.split(version, ".");
		}
	}

	private void def() {
		this.version = VERSION_DEFAULT;
		this.array = new String[] {};
	}

	/**
	 * 采用最后定义优先原则
	 * 方法上的定义覆盖类上面的定义
	 */
	@Override
	public VersionRequestCondition combine(VersionRequestCondition other) {
		return new VersionRequestCondition(other.getVersion());
	}

	// 获取符合的条件
	@Override
	public VersionRequestCondition getMatchingCondition(HttpServletRequest request) {
		// 请求版本号
		String version = this.getRequestVersion(request);
		String[] array = StringUtils.split(version, ".");
		// 当前版本号是默认版本,该版本符合
		if (StringUtils.equals(this.getVersion(), VERSION_DEFAULT)) {
			return this;
		}
		// 如果指定版本号与当前版本号相同,该版本符合
		if (StringUtils.equals(this.version, version)) {
			return this;
		}
		// 如果没有指定版本号,该版本不符合
		if (StringUtils.isBlank(version)) {
			return null;
		}
		// 比较版本号
		int thisLength = this.array.length;
		int hopeLength = array.length;
		int length = thisLength > hopeLength ? hopeLength : thisLength;
		for (int i = 0; i < length; i++) {
			Integer vc = Integer.parseInt(this.array[i]); // 当前的版本
			Integer hc = Integer.parseInt(array[i]); // 希望的版本
			if (hc > vc) {
				// 如果希望的版本高,该版本符合
				return this;
			} else if (hc < vc) {
				// 如果希望的版本低,该版本不符合
				return null;
			}
		}
		// 版本一直一样
		if (thisLength > hopeLength) {
			// 如果当前版本长,该版本不符合
			return null;
		} else if (hopeLength > thisLength) {
			// 如果期望版本长,该版本符合
			return this;
		}
		// 默认不符合
		return null;
	}

	// 版本号排序,版本号越大越靠前
	@Override
	public int compareTo(VersionRequestCondition other, HttpServletRequest request) {

		// 如果当前版本为默认版本,当前版本小
		if (StringUtils.equals(this.version, VERSION_DEFAULT)) {
			return -1;
		}

		// 如果other版本为默认版本,当前版本大
		if (StringUtils.equals(this.version, VERSION_DEFAULT)) {
			return 1;
		}

		// 一样大
		if (StringUtils.equals(other.getVersion(), this.version)) {
			return 0;
		}
		// 谁的版本小,谁大
		String[] otherArray = other.getArray();
		int thisLength = this.array.length;
		int otherLength = otherArray.length;
		int length = thisLength > otherLength ? otherLength : thisLength;
		for (int i = 0; i < length; i++) {
			Integer vc = Integer.parseInt(this.array[i]); // 当前的版本
			Integer oc = Integer.parseInt(otherArray[i]); // 其他的版本
			if (oc > vc) {
				// 如果other的版本高,当前版本大
				return 1;
			} else if (oc < vc) {
				// 如果当前的版本高,当前版本小
				return -1;
			}
		}
		// 版本一直一样
		if (thisLength > otherLength) {
			// 如果当前版本长,当前版本小
			return -1;
		} else if (otherLength > thisLength) {
			// 如果other版本长,当前版本大
			return 1;
		}
		// 默认一样
		return 0;

	}

	// 获取请求的版本
	private String getRequestVersion(HttpServletRequest request) {
		String version = request.getHeader(VERSION_NAME);
		if (StringUtils.isBlank(version)) {
			// 没有版本号
			return "";
		}
		if (!version.matches(VERSION_PATTERN)) {
			// 请求的版本header错误
			return "";
		}
		if (StringUtils.startsWithIgnoreCase(version, "v")) {
			version = version.substring(1);
		}
		return version;
	}

	public String getVersion() {
		return version;
	}

	public String[] getArray() {
		return array;
	}

	@Override
	public int hashCode() {
		return getVersion().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj != null && obj instanceof VersionRequestCondition) {
			VersionRequestCondition c = (VersionRequestCondition) obj;
			return StringUtils.equals(this.getVersion(), c.getVersion());
		}
		return false;
	}

	@Override
	public String toString() {
		return version;
	}

}
