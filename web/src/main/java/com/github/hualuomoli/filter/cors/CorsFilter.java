package com.github.hualuomoli.filter.cors;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.hualuomoli.filter.FilterBean;

public class CorsFilter extends FilterBean implements Filter {

	// allow cross
	private boolean allowCross = false;
	// proxy server domain(http://127.0.0.1:3000)
	// null is all proxy domain
	private Set<String> proxyDomains;

	// cross domain
	private String maxAge = "3600"; // Access-Control-Max-Age
	private String allowCredentials = "true"; // Access-Control-Allow-Credentials
	private String allowMethods = "PUT,POST,GET,DELETE,OPTIONS"; // Access-Control-Allow-Methods
	private String allowHeaders = "x-requested-with,Content-Type,*"; // Access-Control-Allow-Headers

	public void setAllowCross(boolean allowCross) {
		this.allowCross = allowCross;
	}

	public void setProxyDomains(Set<String> proxyDomains) {
		this.proxyDomains = proxyDomains;
	}

	public void setMaxAge(String maxAge) {
		this.maxAge = maxAge;
	}

	public void setAllowCredentials(String allowCredentials) {
		this.allowCredentials = allowCredentials;
	}

	public void setAllowMethods(String allowMethods) {
		this.allowMethods = allowMethods;
	}

	public void setAllowHeaders(String allowHeaders) {
		this.allowHeaders = allowHeaders;
	}

	@Override
	protected void configure() {
		if (logger.isDebugEnabled()) {
			logger.debug("allowCross {}", allowCross);
			logger.debug("proxyDomains {}", proxyDomains);
			logger.debug("maxAge {}", maxAge);
			logger.debug("allowCredentials {}", allowCredentials);
			logger.debug("allowMethods {}", allowMethods);
			logger.debug("allowHeaders {}", allowHeaders);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		this.configAllowCross((HttpServletRequest) request, (HttpServletResponse) response);
		chain.doFilter(request, response);
	}

	private void configAllowCross(HttpServletRequest req, HttpServletResponse res) {
		if (!allowCross || !validate(req)) { // not allow cross domain
			return;
		}
		String origin = req.getHeader("origin");

		// proxy server domain
		if (!isValidPorxyDomain(origin)) {
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("allow cross domain origin {}", origin);
		}

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

	// is origin valid
	private boolean isValidPorxyDomain(String origin) {
		if (proxyDomains == null || proxyDomains.size() == 0) {
			return true;
		}
		return proxyDomains.contains(origin);
	}

	@Override
	public void destroy() {
	}

}
