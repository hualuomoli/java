package com.github.hualuomoli.filter.security;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.error.Result;
import com.github.hualuomoli.filter.FilterBean;
import com.github.hualuomoli.security.Security;
import com.github.hualuomoli.security.entity.User;

/**
 * 权限
 * @author admin
 *
 */
public class SecurityFilter extends FilterBean {

	private static Security security;
	private String securityClassName;

	private String loginUri = "/login"; // login
	private String logoutUri = "/logout"; // logout
	private Set<String> checkUris;

	public void setSecurityClassName(String securityClassName) {
		this.securityClassName = securityClassName;
	}

	public void setLoginUri(String loginUri) {
		this.loginUri = loginUri;
	}

	public void setLogoutUri(String logoutUri) {
		this.logoutUri = logoutUri;
	}

	public void setCheckUris(Set<String> checkUris) {
		this.checkUris = checkUris;
	}

	public Security getSecurity() {
		if (security == null) {
			try {
				security = (Security) Class.forName(securityClassName).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return security;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String servletPath = req.getServletPath();

		if (logger.isDebugEnabled()) {
			logger.debug("servletPath {}", servletPath);
		}

		// 判断是否为登录
		if (this.isLoginPath(servletPath, req, res)) {
			return;
		}

		// 判断是否为登出
		if (this.isLogoutPath(servletPath, req, res)) {
			return;
		}

		if (!this.valid(servletPath, req, res)) {
			return;
		}

		chain.doFilter(request, response);
	}

	// 是否登录
	private boolean isLoginPath(String servletPath, HttpServletRequest req, HttpServletResponse res) throws IOException {

		boolean success = StringUtils.equals(servletPath, loginUri);

		// login
		if (success) {
			String token = UUID.randomUUID().toString().replaceAll("[-]", "");
			User user = new User();
			user.setUsername(req.getParameter(User.USERNAME));
			user.setPassword(req.getParameter(User.PASSWORD));
			user.setToken(token);

			if (this.getSecurity().login(user)) {
				// login success
				this.setSuccess(res);
				res.addHeader(User.TOKEN, token);
			} else {
				// login fail
				this.setError(res, Result.USER_INVALID);
			}
		}

		return success;
	}

	// 是否登出
	private boolean isLogoutPath(String servletPath, HttpServletRequest req, HttpServletResponse res) {

		boolean success = StringUtils.equals(servletPath, logoutUri);

		// login
		if (success) {
			String token = req.getHeader(User.TOKEN);
			this.getSecurity().logout(token);
			// add header
			this.setSuccess(res);
		}

		return success;
	}

	private boolean valid(String servletPath, HttpServletRequest req, HttpServletResponse res) throws IOException {

		if (this.isCheckPath(servletPath)) {
			// check user
			String token = req.getHeader(User.TOKEN);
			// no token
			if (StringUtils.isBlank(token)) {
				this.setNoAuthor(res, Result.USER_NO_LOGIN);
				return false;
			}
			// check login
			if (this.getSecurity().isLogin(token)) {
				// is already login
				return true;
			} else {
				// login overtime
				this.setNoAuthor(res, Result.USER_OVERTIME);
				return false;
			}
		}

		return true;
	}

	// check user or not
	private boolean isCheckPath(String servletPath) {
		if (checkUris == null && checkUris.isEmpty()) {
			return true;
		}
		for (String checkUri : checkUris) {
			if (StringUtils.startsWith(servletPath, checkUri)) {
				// check login
				return true;
			}
		}
		return false;
	}

}
