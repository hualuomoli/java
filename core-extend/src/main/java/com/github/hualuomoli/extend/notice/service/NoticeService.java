package com.github.hualuomoli.extend.notice.service;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.extend.notice.Noticer;
import com.github.hualuomoli.extend.notice.Notifer;
import com.google.common.collect.Sets;

@SuppressWarnings("rawtypes")
@Service(value = "com.github.hualuomoli.extend.notice.service.NoticeService")
@Transactional(readOnly = true)
public class NoticeService implements ApplicationContextAware {

	private static final Object OBJECT = new Object();
	private Set<Notifer> notifers = Sets.newHashSet();

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public void notice(Noticer noticer) {
		synchronized (OBJECT) {
			if (noticer == null) {
				return;
			}
			for (Notifer notifer : notifers) {
				if (notifer.support(noticer.getClass())) {
					notifer.notice(noticer);
				}
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, Notifer> map = applicationContext.getBeansOfType(Notifer.class);
		if (map == null || map.size() == 0) {
			return;
		}
		for (Notifer notifer : map.values()) {
			notifers.add(notifer);
		}
	}

}
