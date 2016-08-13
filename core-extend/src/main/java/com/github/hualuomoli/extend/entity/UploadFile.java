package com.github.hualuomoli.extend.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.BaseField;

/**
 * 文件信息
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
@EntityTable(comment = "文件上传")
public class UploadFile extends BaseField {

	@EntityColumn(comment = "上传表单名", length = 100)
	private String fieldname;
	@EntityColumn(comment = "文件原始名称", length = 100)
	private String originalFilename;
	@EntityColumn(comment = "文件大小")
	private Long size;
	@EntityColumn(comment = "文件协议")
	private String mimetype;
	@EntityColumn(comment = "文件MD5", length = 200)
	private String fileMd5;

	@EntityColumn(comment = "文件名(含后缀)", length = 100)
	private String fileFullname;
	@EntityColumn(comment = "文件名(不含后缀)", length = 100)
	private String filename;
	@EntityColumn(comment = "文件扩展名", length = 20)
	private String fileSuffix;
	@EntityColumn(comment = "文件绝对路径", length = 200)
	private String fileAbsolutepath;

	@EntityColumn(comment = "文件访问URL", length = 200)
	private String url;
	@EntityColumn(comment = "服务器URL", length = 100)
	private String serverUrl;
	@EntityColumn(comment = "相对服务器的URL", length = 100)
	private String relativeUrl;

	public UploadFile() {
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public String getFileFullname() {
		return fileFullname;
	}

	public void setFileFullname(String fileFullname) {
		this.fileFullname = fileFullname;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getFileAbsolutepath() {
		return fileAbsolutepath;
	}

	public void setFileAbsolutepath(String fileAbsolutepath) {
		this.fileAbsolutepath = fileAbsolutepath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getRelativeUrl() {
		return relativeUrl;
	}

	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}

}
