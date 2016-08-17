package com.github.hualuomoli.extend.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.extend.base.entity.BaseUploadFile;
import com.github.hualuomoli.extend.service.FileService;
import com.google.common.collect.Maps;

/**
 * 上传
 * @author hualuomoli
 *
 */
@RequestMapping(value = "/ueditor")
@Controller(value = "com.github.hualuomoli.extend.web.UeditorController")
public class UeditorController {

	@Autowired
	private FileService fileService;

	// 富文本编辑器,上传文件
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { "multipart/form-data" }, produces = { "application/json" })
	@ResponseBody
	public String ueditor(@RequestParam(value = "uploadFile", required = true) MultipartFile uploadFile, HttpServletRequest request) {

		// 处理者
		BaseUploadFile baseUploadFile = fileService.save(uploadFile);

		Map<String, Object> map = Maps.newHashMap();
		map.put("state", "SUCCESS");
		map.put("url", baseUploadFile.getUrl());
		map.put("title", baseUploadFile.getFilename());
		map.put("original", baseUploadFile.getOriginalFilename());

		return JsonUtils.toJson(map);

	}

	// 图片地址替换
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/x-www-form-urlencoded" }, produces = { "application/json" })
	@ResponseBody
	public String ueditor(HttpServletRequest request) {

		String[] values = request.getParameterValues("source[]");

		Map<String, Object> map = Maps.newHashMap();
		map.put("list", values);

		return JsonUtils.toJson(map);

	}

	public static class Source {
		private String state = "SUCCESS";
		private boolean source = true;
		private String url;

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public boolean isSource() {
			return source;
		}

		public void setSource(boolean source) {
			this.source = source;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}

}
