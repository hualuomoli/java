package com.github.hualuomoli.ret.page;

import com.github.hualuomoli.ret.none.NoDataMessageReturn;
import com.github.hualuomoli.ret.page.entity.Page;

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
public class PageDataMessageReturn<T> extends NoDataMessageReturn {

	private static final long serialVersionUID = -6330609865567723667L;

	private Page<T> data;

	public PageDataMessageReturn() {
	}

	public Page<T> getData() {
		return data;
	}

	public void setData(Page<T> data) {
		this.data = data;
	}

}
