package com.github.hualuomoli.mvc.ramlapi.result;

import com.github.hualuomoli.mvc.ramlapi.ResultAbstract;
import com.github.hualuomoli.mvc.ramlapi.result.entity.ResultPage;

/**
 * return page data
 * @description
 * {
 * 	"code" : "0",
 * 	"data" : {
 * 		"total" : 500,
 * 		"pageSize" : 50,
 * 		"pageNumber" : 3,
 * 		"list" : [
 * 			{
 * 				"name" : "jack",
 * 				"age" : 12
 * 			},
 * 			{
 * 				"name" : "tony",
 * 				"age" : 20
 * 			},
 * 		]
 * 	}
 * }
 * @author hualuomoli
 *
 */
public class PageData<T> extends ResultAbstract {

	private ResultPage<T> data;

	public PageData() {
	}

	public ResultPage<T> getData() {
		return data;
	}

	public void setData(ResultPage<T> data) {
		this.data = data;
	}

}
