package com.github.hualuomoli.extend.sms.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.commons.util.RandomUtils;
import com.github.hualuomoli.extend.base.entity.BaseSms;
import com.github.hualuomoli.extend.base.service.BaseSmsService;
import com.github.hualuomoli.extend.constant.DataType;
import com.github.hualuomoli.extend.sms.mapper.SmsMapper;
import com.google.common.collect.Lists;

@Service(value = "com.github.hualuomoli.extend.sms.service.SmsService")
@Transactional(readOnly = true)
public class SmsService {

	private static final Integer DEFAULT_TIMES = 30; // 30分钟

	@Autowired
	private BaseSmsService baseSmsService;
	@Autowired
	private SmsMapper smsMapper;

	/**
	 * 保存短信内容
	 * @param phone 手机号码
	 * @param checkCode 手机验证码
	 * @param type 短信类别
	 * @param contentTemplate 短信模板,如"欢迎使用平台注册,您的手机号码是:phone,短信验证码:checkCode,有效时间:validTime"
	 */
	@Transactional(readOnly = false)
	public BaseSms save(String phone, String checkCode, Integer type, String contentTemplate) {
		return this.save(phone, checkCode, type, DEFAULT_TIMES, contentTemplate);
	}

	/**
	 * 保存短信内容
	 * @param phone 手机号码
	 * @param checkCode 手机验证码
	 * @param type 短信类别
	 * @param minute 短信有效时间,单位为分钟
	 * @param contentTemplate 短信模板,如"欢迎使用平台注册,您的手机号码是:phone,短信验证码:checkCode,有效时间:validTime"
	 */
	@Transactional(readOnly = false)
	public BaseSms save(String phone, String checkCode, Integer type, Integer minute, String contentTemplate) {

		// 以前的短信失效
		smsMapper.invalid(phone, type, DataType.SMS_INVALID.getValue(), DataType.SMS_INVALID.getName());

		// 发送短信
		String validTime = minute + "分钟";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minute);
		BaseSms baseSms = new BaseSms();
		baseSms.setId(RandomUtils.getUUID());
		baseSms.setPhone(phone);
		baseSms.setCheckCode(checkCode);
		baseSms.setType(type);
		baseSms.setValidTime(validTime);
		baseSms.setValidDate(calendar.getTime());
		baseSms.setContent(contentTemplate.replace(":phone", phone).replace("checkCode", checkCode).replace(":validTime", validTime));
		baseSms.setState(DataType.SMS_NOMAL.getValue());
		baseSms.setStateName(DataType.SMS_NOMAL.getName());

		baseSmsService.insert(baseSms);

		return baseSms;
	}

	/**
	 * 短信验证码是否有效
	 * @param phone 手机号码
	 * @param checkCode 验证码
	 * @param minute 有效时长,单位为分钟
	 * @param type 短信类别
	 * @return 是否验证成功
	 */
	@Transactional(readOnly = false)
	public boolean valid(String phone, String checkCode, Integer type) {
		return this.valid(phone, checkCode, type, DEFAULT_TIMES);
	}

	/**
	 * 短信验证码是否有效
	 * @param phone 手机号码
	 * @param checkCode 验证码
	 * @param type 短信类别
	 * @param minute 有效时长,单位为分钟
	 * @return 是否验证成功
	 */
	@Transactional(readOnly = false)
	public boolean valid(String phone, String checkCode, Integer type, Integer minute) {

		BaseSms baseSms = new BaseSms();
		baseSms.setPhone(phone);
		baseSms.setCheckCode(checkCode);
		baseSms.setType(type);
		baseSms.setState(DataType.SMS_NOMAL.getValue());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		baseSms.setValidDateGreaterThan(calendar.getTime());
		calendar.add(Calendar.MINUTE, minute);
		baseSms.setValidDateLessEqual(calendar.getTime());

		List<BaseSms> list = baseSmsService.findList(baseSms);
		// 没有数据
		if (list == null || list.size() == 0) {
			return false;
		}
		// 验证过,设置为已验证
		List<String> ids = Lists.newArrayList();
		for (BaseSms bs : list) {
			ids.add(bs.getId());
		}
		smsMapper.checked(ids, DataType.SMS_CHECKED.getValue(), DataType.SMS_CHECKED.getName());

		return true;
	}

}
