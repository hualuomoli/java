package com.github.hualuomoli.filter.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.filter.FilterBean;
import com.github.hualuomoli.ret.none.NoDataMessageReturn;
import com.github.hualuomoli.security.Security;

public class SecurityFilter extends FilterBean {

	private static Security security;
	private String securityClassName;

	private Set<String> checkUris;

	public void setSecurityClassName(String securityClassName) {
		this.securityClassName = securityClassName;
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
		Enumeration<String> names = req.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			System.out.println(name + " = " + req.getHeader(name));
		}
		String token = req.getHeader(Security.TOKEN);

		if (this.isValidAllowUris(servletPath, token)) {
			chain.doFilter(request, response);
		} else {
			// check security
			if (StringUtils.isEmpty(token)) {
				// not login
				res.setStatus(NO_LOGIN);
				PrintWriter writer = response.getWriter();
				writer.write(new NoDataMessageReturn(String.valueOf(NO_LOGIN), "please login first").toJson());
				writer.flush();
			} else {
				// time out
				res.setStatus(LOGIN_OUT_TIME);
				PrintWriter writer = response.getWriter();
				writer.write(new NoDataMessageReturn(String.valueOf(LOGIN_OUT_TIME), "session time out, please relogin.").toJson());
				writer.flush();
			}
		}

	}

	private boolean isValidAllowUris(String servletPath, String token) {
		if (checkUris == null && checkUris.isEmpty()) {
			return true;
		}
		for (String checkUri : checkUris) {
			if (StringUtils.startsWith(servletPath, checkUri)) {
				// check login
				return this.getSecurity().isLogin(token);
			}
		}
		return true;
	}

}
