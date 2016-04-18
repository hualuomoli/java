package com.github.hualuomoli.ret.list;

import java.util.List;

import com.github.hualuomoli.ret.none.NoDataMessageReturn;

/**
 * return list data
 * @description
 * {
 * 	"code" : "0",
 * 	"data" : [
 * 		{
 * 			"name" : "jack",
 * 			"age" : 12
 * 		},
 * 		{
 * 			"name" : "tony",
 * 			"age" : 20
 * 		}
 * 	]
 * }
 * @author hualuomoli
 *
 */
public class ListDataMessageReturn<T> extends NoDataMessageReturn {

	private static final long serialVersionUID = -361084718831818979L;

	private List<T> data;

	public ListDataMessageReturn() {
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
