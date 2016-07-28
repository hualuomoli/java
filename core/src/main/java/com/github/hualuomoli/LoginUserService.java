package com.github.hualuomoli;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录用户service
 * @author hualuomoli
 *
 */
public interface LoginUserService {

	public static final String TOKEN_NAME = "token";

	/**
	 * 获取登录用户名
	 * @return 登录用户名
	 */
	String getUsername();

	/**
	 * 获取登录用户
	 * @return 登录用户
	 */
	<T extends Serializable> LoginUser<T> getLoginUser();

	/**
	 * 获取登录用户
	 * @return 登录用户
	 */
	<T extends Serializable> T getUser();

	/**
	 * 设置用户
	 * @param username 用户名
	 * @param obj 用户信息
	 */
	<T extends Serializable> void setUser(String username, T obj);

	/**
	 * 获取当前时间
	 * @return 当前时间
	 */
	Date getCurrentDate();

	/** 登录用户 */
	public static class LoginUser<T extends Serializable> {
		private String username;
		private T obj;

		public LoginUser() {
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public T getObj() {
			return obj;
		}

		public void setObj(T obj) {
			this.obj = obj;
		}

	}

}
