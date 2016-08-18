package com.github.hualuomoli.demo.extend.role.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.demo.extend.role.service.NotiferTwo.NoticerDemo;
import com.github.hualuomoli.extend.notice.Noticer;
import com.github.hualuomoli.extend.notice.Notifer;

@Service(value = "com.github.hualuomoli.demo.extend.role.service.NotiferTwo")
public class NotiferTwo implements Notifer<NoticerDemo> {

	private static final Logger logger = LoggerFactory.getLogger(NotiferTwo.class);

	@Override
	public boolean support(Class<?> cls) {
		return cls == Object.class;
	}

	@Override
	public void notice(NoticerDemo noticer) {
		logger.debug("noticer demo.");
	}

	public static class NoticerDemo implements Noticer {

	}

}
