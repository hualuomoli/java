package com.github.hualuomoli.extend.file.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.hualuomoli.extend.base.entity.BaseUploadFile;
import com.github.hualuomoli.extend.file.dealer.FileUploadDealer;
import com.github.hualuomoli.extend.file.service.FileService;
import com.github.hualuomoli.extend.rest.AppRestResponse;
import com.github.hualuomoli.mvc.annotation.RequestToken;
import com.google.common.collect.Lists;

/**
 * 文件
 * @author hualuomoli
 *
 */
@RequestMapping(value = "/file")
@Controller(value = "com.github.hualuomoli.extend.file.web.FileController")
public class FileController implements ApplicationContextAware {

	private List<FileUploadDealer> dealerList = Lists.newArrayList();
	@Autowired
	private FileService fileService;

	// 上传
	@RequestToken
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = { "multipart/form-data" }, produces = { "application/json" })
	@ResponseBody
	public String upload(@RequestParam(value = "uploadFile", required = true) MultipartFile uploadFile, HttpServletRequest request) {
		String headerUploadType = request.getHeader("upload-type");

		// 处理者
		FileUploadDealer dealer = this.getFileUploadDealer(headerUploadType);
		BaseUploadFile baseUploadFile = null;

		if (dealer == null) {
			// 上传
			baseUploadFile = fileService.save(uploadFile);
		} else if (dealer.getDealer() == null) {
			// 上传
			baseUploadFile = fileService.save(uploadFile);
			// 处理
			dealer.deal(baseUploadFile);
		} else {
			// 上传
			baseUploadFile = fileService.save(uploadFile, dealer.getDealer());
			// 处理
			dealer.deal(baseUploadFile);
		}

		return AppRestResponse.getObjectData("file", baseUploadFile);

	}

	// 获取上传处理者
	private FileUploadDealer getFileUploadDealer(String headerUploadType) {
		if (dealerList == null || dealerList.size() == 0) {
			return null;
		}
		for (FileUploadDealer fileUploadDealer : dealerList) {
			if (StringUtils.equals(headerUploadType, fileUploadDealer.supportHeader())) {
				return fileUploadDealer;
			}
		}
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// FileUploadDealer
		Map<String, FileUploadDealer> dealers = applicationContext.getBeansOfType(FileUploadDealer.class);
		if (dealers == null || dealers.size() == 0) {
			return;
		}
		for (FileUploadDealer fileUploadDealer : dealers.values()) {
			dealerList.add(fileUploadDealer);
		}
	}

}
