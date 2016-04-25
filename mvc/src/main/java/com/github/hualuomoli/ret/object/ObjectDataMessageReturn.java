package com.github.hualuomoli.ret.object;

import com.github.hualuomoli.ret.none.NoDataMessageReturn;

/**
 * return object data
 * @description
 * {
 * 	"code" : "0",
 * 	"data" : {
 * 		"username" : "hualuomoli",
 * 		"nickname" : "花落莫离"
 * 	}
 * }
 * @author hualuomoli
 *
 * @param <T> data type
 */
public class ObjectDataMessageReturn<T> extends NoDataMessageReturn {

	private static final long serialVersionUID = -3895536923345693226L;

	private T data;

	public ObjectDataMessageReturn() {
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
