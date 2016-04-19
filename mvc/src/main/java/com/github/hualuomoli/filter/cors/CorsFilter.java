package com.github.hualuomoli.filter.cors;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.filter.FilterBean;

/**
 * 跨域拦截器
 * @author hualuomoli
 *
 */
public class CorsFilter extends FilterBean {

	// proxy server domain(http://127.0.0.1:3000)
	// null is all proxy domain
	private Set<String> proxyDomains;

	// cross domain
	private String maxAge = "3600"; // Access-Control-Max-Age
	private String allowCredentials = "true"; // Access-Control-Allow-Credentials
	private String allowMethods = "PUT,POST,GET,DELETE,OPTIONS"; // Access-Control-Allow-Methods
	private String allowHeaders = "x-requested-with,Content-Type,*"; // Access-Control-Allow-Headers

	public void setProxyDomains(Set<String> proxyDomains) {
		this.proxyDomains = proxyDomains;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// add cross header
		if (!this.addCrossHeader((HttpServletRequest) request, (HttpServletResponse) response)) {
			return;
		}
		// chain
		chain.doFilter(request, response);
	}

	// add cross header
	private boolean addCrossHeader(HttpServletRequest req, HttpServletResponse res) {

		String origin = req.getHeader("origin");

		// call this server by browser, postman, mocha,
		// origin is null, not use cross domain
		if (StringUtils.isEmpty(origin)) {
			return true;
		}

		// show log
		if (logger.isDebugEnabled()) {
			logger.debug("origin {}", origin);
		}

		// check proxy server
		boolean validation = this.valid(origin);
		if (validation) {
			if (logger.isDebugEnabled()) {
				logger.debug("allow cross domain origin {}", origin);
			}
			this.addHeader(res, origin);
		} else {
			// not cross domain
			// TODO
		}

		return validation;
	}

	// is origin valid
	private boolean valid(String origin) {
		if (proxyDomains == null || proxyDomains.size() == 0) {
			return true;
		}
		return proxyDomains.contains(origin);
	}

	// set header
	private void addHeader(HttpServletResponse res, String origin) {
		// 授权的源控制
		res.setHeader("Access-Control-Allow-Origin", origin);
		// 授权的时间
		res.setHeader("Access-Control-Max-Age", maxAge);
		// 控制是否开启与Ajax的Cookie提交方式
		res.setHeader("Access-Control-Allow-Credentials", allowCredentials);
		// 允许请求的HTTP Method
		res.setHeader("Access-Control-Allow-Methods", allowMethods);
		// 控制哪些header能发送真正的请求
		res.setHeader("Access-Control-Allow-Headers", allowHeaders);
	}

}
