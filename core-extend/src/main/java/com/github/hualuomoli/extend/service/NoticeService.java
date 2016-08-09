package com.github.hualuomoli.extend.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.extend.notice.Noticer;
import com.github.hualuomoli.extend.notice.Notifer;
import com.google.common.collect.Sets;

@SuppressWarnings("rawtypes")
@Service(value = "com.github.hualuomoli.extend.notice.NoticeService")
@Transactional(readOnly = true)
public class NoticeService {

	private static final Object OBJECT = new Object();
	private Set<Notifer> notifers = Sets.newHashSet();

	public void addNotifer(Notifer notifer) {
		synchronized (OBJECT) {
			if (!notifers.contains(notifer)) {
				notifers.add(notifer);
			}
		}
	}

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

}
