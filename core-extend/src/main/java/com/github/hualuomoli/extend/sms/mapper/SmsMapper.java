package com.github.hualuomoli.extend.sms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "com.github.hualuomoli.extend.sms.mapper.SmsMapper")
public interface SmsMapper {

	/**
	 * 短信失效(发送短信时,原来的短信均失效)
	 * @param phone 手机号码
	 * @param type 短信类型
	 * @param state 失效状态
	 * @param stateName 失效状态名称
	 */
	void invalid(@Param(value = "phone") String phone, @Param(value = "type") Integer type, @Param(value = "state") Integer state,
			@Param(value = "stateName") String stateName);

	/**
	 * 短信已验证
	 * @param ids 短信ID
	 * @param state 已验证状态
	 * @param stateName 已验证状态名称
	 */
	void checked(@Param(value = "ids") List<String> ids, @Param(value = "state") Integer state, @Param(value = "stateName") String stateName);

}
