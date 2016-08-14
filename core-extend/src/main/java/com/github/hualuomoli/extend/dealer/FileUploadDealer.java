package com.github.hualuomoli.extend.dealer;

import com.github.hualuomoli.extend.base.entity.BaseUploadFile;
import com.github.hualuomoli.extend.service.FileService.Dealer;

/**
 * 文件处理者
 * @author hualuomoli
 *
 */
public interface FileUploadDealer {

	// 支持的header值
	String supportHeader();

	// 文件保存处理者
	Dealer getDealer();

	// 处理上传文件
	void deal(BaseUploadFile baseUploadFile);

}
