package com.github.hualuomoli.raml.join;

import com.github.hualuomoli.raml.join.adaptor.util.AdaptorUtils.Translate;

/**
 * 适配器抽象
 * @author hualuomoli
 *
 */
public abstract class AdaptorAbstract implements Adaptor {

	private MethodAdaptor methodAdaptor;

	public AdaptorAbstract() {
	}

	public AdaptorAbstract(MethodAdaptor methodAdaptor) {
		this.methodAdaptor = methodAdaptor;
	}

	@Override
	public String getActionData(Translate translate) {
		return methodAdaptor.getMethodData(translate);
	}

	// 方法适配器
	public static interface MethodAdaptor {

		/**
		 * 获取方法的数据
		 * @param translate 转换实体类
		 * @param transfer 转换器
		 * @return 方法的数据
		 */
		String getMethodData(Translate translate);

	}

}
