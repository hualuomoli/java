package com.github.hualuomoli.mvc.ramlapi.result;

import java.util.List;

import com.github.hualuomoli.mvc.ramlapi.ResultAbstract;

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
public class ListData<T> extends ResultAbstract {

	private List<T> data;

	public ListData() {
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
