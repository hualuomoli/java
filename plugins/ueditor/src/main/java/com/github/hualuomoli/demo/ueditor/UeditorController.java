package com.github.hualuomoli.demo.ueditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.hualuomoli.base.util.BaseUtils;
import com.github.hualuomoli.commons.json.JsonMapper;
import com.github.hualuomoli.commons.util.CharsetUtils;
import com.github.hualuomoli.plugin.ueditor.Storage;
import com.google.common.collect.Maps;

@RequestMapping(value = "/ueditor")
@Controller(value = "com.github.hualuomoli.demo.ueditor.UeditorController")
public class UeditorController {

	private static final Logger logger = LoggerFactory.getLogger(UeditorController.class);

	private static String configData = null;

	// 配置文件信息
	@RequestMapping(value = "", method = { RequestMethod.GET })
	public void config(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String action = request.getParameter("action");
		if (logger.isDebugEnabled()) {
			logger.debug("request GET, action {}", action);
		}
		switch (action) {
		case "config":
			if (configData == null) {
				String config = StreamUtils.copyToString(this.getClass().getClassLoader().getResourceAsStream("config/ueditor.json"), CharsetUtils.UTF8);
				configData = config.replaceAll("/\\*[\\s\\S]*?\\*/", "");
			}
			response.addHeader("Content-Type", "text/html");
			this.result(request, response, configData);
			break;
		default:
			throw new IOException("there is no dealer to deal with " + action);
		}

	}

	// 文件上传
	@RequestMapping(value = "", method = { RequestMethod.POST }, consumes = { "multipart/*" })
	public void upload(@RequestParam(value = "upfile", required = true) MultipartFile upfile, HttpServletRequest request, HttpServletResponse response) throws IOException {

		String action = request.getParameter("action");
		if (logger.isDebugEnabled()) {
			logger.debug("request POST, MimeType multipart, action {}", action);
		}

		String originalFilename = upfile.getOriginalFilename();
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

		String filename = BaseUtils.getRandomId();
		upfile.transferTo(new File(this.getUploadFolder(), filename));
		Map<String, Object> map = Maps.newHashMap();
		map.put("url", "http://localhost:80/web/ueditor/file/" + filename);
		map.put("state", "SUCCESS");
		map.put("title", filename);
		map.put("original", originalFilename);
		map.put("type", suffix);
		map.put("size", upfile.getSize());

		String data = JsonMapper.toJsonString(map);

		response.addHeader("Content-Type", "application/json");
		this.result(request, response, data);
	}

	// 涂鸦
	@RequestMapping(value = "", method = { RequestMethod.POST }, consumes = { "application/x-www-form-urlencoded" })
	public void uploada(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String action = request.getParameter("action");
		if (logger.isDebugEnabled()) {
			logger.debug("request POST, MimeType application/x-www-form-urlencoded, action {}", action);
		}

		String content = request.getParameter("upfile");
		String filename = BaseUtils.getRandomId();

		Storage.saveFile(content, this.getUploadFolder(), filename);

		Map<String, Object> map = Maps.newHashMap();
		map.put("url", "http://localhost:80/web/ueditor/file/" + filename);
		map.put("state", "SUCCESS");
		map.put("title", filename);
		map.put("original", filename);

		String data = JsonMapper.toJsonString(map);

		response.addHeader("Content-Type", "application/json");
		this.result(request, response, data);
	}

	// 返回结果
	private void result(HttpServletRequest request, HttpServletResponse response, String result) throws IOException {
		String callback = request.getParameter("callback");
		String data;
		if (StringUtils.isNotBlank(callback)) {
			data = callback + "(" + result + ")";
		} else {
			data = result;
		}
		response.getOutputStream().write(data.getBytes(CharsetUtils.UTF8));
	}

	// 下载文件
	@RequestMapping(value = "/file/{id}")
	public void download(@PathVariable(value = "id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream outputStream = response.getOutputStream();
		StreamUtils.copy(new FileInputStream(new File(this.getUploadFolder(), id)), outputStream);
		outputStream.flush();
		outputStream.close();
	}

	// 获取上传目录
	private String getUploadFolder() {
		return "E:/upload";
	}

}
