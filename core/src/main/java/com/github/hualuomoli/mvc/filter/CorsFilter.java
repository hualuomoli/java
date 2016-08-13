package com.github.hualuomoli.mvc.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.util.YamlUtils;

/**
 * 跨域过滤器
 * @author hualuomoli
 *
 */
public class CorsFilter extends MvcFilter {

	private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);

	private boolean valid = BooleanUtils.toBooleanObject(YamlUtils.getInstance().getString("filter", "cors", "valid"));
	private List<Cors> corses = YamlUtils.getInstance().getList("servers", Cors.class, "filter", "cors");

	private static final String DEFAULT_MAX_AGE = "3600"; // Access-Control-Max-Age
	private static final String DEFAULT_ALLOW_CREDENTIALS = "true"; // Access-Control-Allow-Credentials
	private static final String DEFAULT_ALLOW_METHODS = "PUT,POST,GET,DELETE,OPTIONS"; // Access-Control-Allow-Methods
	private static final String DEFAULT_ALLOW_HEADERS = "x-requested-with,Content-Type,*"; // Access-Control-Allow-Headers

	@Override
	public void filter(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 不适用跨域
		if (!valid) {
			return;
		}

		// 来源服务器
		String origin = req.getHeader("origin");

		// 不适用跨域
		if (StringUtils.isBlank(origin)) {
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cors origin server {}", origin);
		}

		addCors(req, res, origin);

	}

	// 增加跨域
	private void addCors(HttpServletRequest req, HttpServletResponse res, String origin) {
		// 没有配置
		if (corses == null || corses.size() == 0) {
			return;
		}
		Cors c = null;
		for (Cors cors : corses) {
			// 任意的域
			if (StringUtils.equals(cors.allowOrigin, "*")) {
				c = cors;
				break;
			}
			// 允许的域
			if (StringUtils.equals(cors.allowOrigin, origin)) {
				c = cors;
				break;
			}
		}
		// 不允许
		if (c == null) {
			return;
		}
		// 配置跨域
		// 授权的源控制
		res.setHeader("Access-Control-Allow-Origin", origin);
		// 授权的时间
		res.setHeader("Access-Control-Max-Age", c.maxAge == null ? DEFAULT_MAX_AGE : c.maxAge);
		// 控制是否开启与Ajax的Cookie提交方式
		res.setHeader("Access-Control-Allow-Credentials", c.allowCredentials == null ? DEFAULT_ALLOW_CREDENTIALS : c.allowCredentials);
		// 允许请求的HTTP Method
		res.setHeader("Access-Control-Allow-Methods", c.allowMethods == null ? DEFAULT_ALLOW_METHODS : c.allowMethods);
		// 控制哪些header能发送真正的请求
		res.setHeader("Access-Control-Allow-Headers", c.allowHeaders == null ? DEFAULT_ALLOW_HEADERS : c.allowHeaders);

	}

	public static class Cors {
		private String allowOrigin;
		private String maxAge;
		private String allowCredentials;
		private String allowMethods;
		private String allowHeaders;

		public Cors() {
		}

		public String getAllowOrigin() {
			return allowOrigin;
		}

		public void setAllowOrigin(String allowOrigin) {
			this.allowOrigin = allowOrigin;
		}

		public String getMaxAge() {
			return maxAge;
		}

		public void setMaxAge(String maxAge) {
			this.maxAge = maxAge;
		}

		public String getAllowCredentials() {
			return allowCredentials;
		}

		public void setAllowCredentials(String allowCredentials) {
			this.allowCredentials = allowCredentials;
		}

		public String getAllowMethods() {
			return allowMethods;
		}

		public void setAllowMethods(String allowMethods) {
			this.allowMethods = allowMethods;
		}

		public String getAllowHeaders() {
			return allowHeaders;
		}

		public void setAllowHeaders(String allowHeaders) {
			this.allowHeaders = allowHeaders;
		}

	}

}
