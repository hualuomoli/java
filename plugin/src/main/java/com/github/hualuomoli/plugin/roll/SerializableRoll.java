package com.github.hualuomoli.plugin.roll;

import java.io.Serializable;

/**
 * 轮询
 * @author hualuomoli
 *
 */
public interface SerializableRoll {

	/**
	 * 添加数据轮询
	 * @param data 		数据
	 * @param dealer 	处理者
	 */
	<T extends SerializableDealer> void pushSerializable(Serializable data, Class<T> dealer);

	/**
	 * 添加数据轮询
	 * @param data 		数据
	 * @param dealer 	处理者
	 * @param frequency 调度频度
	 */
	<T extends SerializableDealer> void pushSerializable(Serializable data, Class<T> dealer, String frequency);

	/**
	 * 添加数据轮询
	 * @param data 		数据
	 * @param dealer 	处理者
	 * @param frequency 调度频度
	 * @param priority	优先级
	 */
	<T extends SerializableDealer> void pushSerializable(Serializable data, Class<T> dealer, String frequency, Integer priority);

	// 字符串处理者
	public static interface SerializableDealer {

		/**
		 * 处理,如果处理成功返回true,否则轮询通知直到最大通知时间(如30分钟)
		 * @param data 数据
		 * @return 通知是否成功
		 */
		boolean deal(Serializable data);

	}

}
