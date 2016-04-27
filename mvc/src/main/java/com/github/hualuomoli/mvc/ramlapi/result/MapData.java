package com.github.hualuomoli.mvc.ramlapi.result;

import com.github.hualuomoli.mvc.ramlapi.ResultAbstract;

/**
 * return map data
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
public class MapData<T> extends ResultAbstract {

	private T data;

	public MapData() {
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
