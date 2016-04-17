package com.github.hualuomoli.web.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * shiro tool
 * 
 * @author liubaoquan
 *
 */
public class ShiroUtils {

	private static final Logger logger = LoggerFactory.getLogger(ShiroUtils.class);

	private ShiroUtils() {
	}

	public static void login(String username, String password) {
		Subject subject = getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken();
		token.setUsername(username);
		subject.login(token);
	}

	public static void logout() {
		getSubject().logout();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static Object getPrincipal() {
		return getSubject().getPrincipal();
	}

	public static Session getSession() {
		return getSubject().getSession();
	}

	public static void put(Object key, Object value) {
		Session session = getSession();
		session.setAttribute(key, value);
		if (logger.isDebugEnabled()) {
			logger.debug("session timeout default {} s", session.getTimeout() / 1000);
		}
	}

	public static Object get(Object key) {
		return getSession().getAttribute(key);
	}

}
