package com.github.hualuomoli.mvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class MvcFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			this.filter((HttpServletRequest) request, (HttpServletResponse) response);
			// chain
			chain.doFilter(request, response);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	// filter
	public abstract void filter(HttpServletRequest req, HttpServletResponse res) throws Exception;

	@Override
	public void destroy() {

	}

}
