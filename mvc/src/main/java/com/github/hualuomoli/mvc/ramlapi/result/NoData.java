package com.github.hualuomoli.mvc.ramlapi.result;

import com.github.hualuomoli.mvc.ramlapi.ResultAbstract;

/**
 * 不返回数据
 * @author admin
 *
 */
public class NoData extends ResultAbstract {

	public NoData() {
		this(CODE_SUCCESS, MSG_SUCCESS);
	}

	public NoData(String msg) {
		this(CODE_SUCCESS, msg);
	}

	public NoData(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
