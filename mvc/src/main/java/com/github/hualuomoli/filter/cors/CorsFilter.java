package com.github.hualuomoli.filter.cors;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.hualuomoli.filter.FilterBean;
import com.github.hualuomoli.ret.none.NoDataMessageReturn;

/**
 * 跨域拦截器
 * @author hualuomoli
 *
 */
public class CorsFilter extends FilterBean {

	// allow cross
	private boolean allowCross;
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String origin = req.getHeader("origin");
		if (this.allowCross && this.isValidPorxyDomain(origin)) {
			this.setCross(res, origin);
			chain.doFilter(request, response);
		} else {
			res.setStatus(CAN_NOT_CROSS);
			PrintWriter writer = res.getWriter();
			writer.write(new NoDataMessageReturn(String.valueOf(CAN_NOT_CROSS), "can not cross domain " + origin).toJson());
			writer.flush();
		}
	}

	// set cross
	private void setCross(HttpServletResponse res, String origin) {
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

}
