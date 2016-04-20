package com.github.hualuomoli.mvc.exception;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.github.hualuomoli.commons.constant.Charset;
import com.github.hualuomoli.mvc.annotation.ContentType;
import com.github.hualuomoli.mvc.exception.entity.ErrorData;
import com.github.hualuomoli.mvc.security.exception.MvcException;

@Service(value = "com.github.hualuomoli.mvc.exception.HandlerException")
public class HandlerException implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

		if (ex instanceof MvcException) {

			MvcException e = (MvcException) ex;

			// set status
			response.setStatus(e.getStatus());
			// set content-type
			response.setContentType(ContentType.JSON);
			// set encoding
			response.setCharacterEncoding(Charset.UTF8.getEncoding());
			// set header
			response.setHeader(ErrorData.CODE, e.getErrorCode());
			response.setHeader(ErrorData.MSG, e.getErrorMsg());

			// flush data
			this.flushData(response, e);

		}

		return new ModelAndView();
	}

	// flush
	void flushData(HttpServletResponse response, MvcException e) {
		try {
			ErrorData data = new ErrorData();
			data.setErrorCode(e.getErrorCode());
			data.setErrorMsg(e.getMessage());

			PrintWriter writer = response.getWriter();
			writer.write(data.toJson());
			writer.flush();
		} catch (Exception e1) {
		}
	}

}
