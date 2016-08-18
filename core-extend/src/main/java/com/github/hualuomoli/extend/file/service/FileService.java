package com.github.hualuomoli.extend.file.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.hualuomoli.commons.util.RandomUtils;
import com.github.hualuomoli.extend.base.entity.BaseUploadFile;
import com.github.hualuomoli.extend.base.service.BaseUploadFileService;
import com.github.hualuomoli.util.ApplicationUtils;

@Service(value = "com.github.hualuomoli.extend.file.service.FileService")
@Transactional(readOnly = true)
public class FileService {

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	private static final Dealer DEFAULT_DEALER = new DealerAdaptor();

	@Autowired
	private BaseUploadFileService baseUploadFileService;

	// 保存
	@Transactional(readOnly = false)
	public BaseUploadFile save(MultipartFile file) {
		return this.save(file, DEFAULT_DEALER);
	}

	// 保存
	@Transactional(readOnly = false)
	public BaseUploadFile save(MultipartFile upload, Dealer dealer) {
		if (upload == null) {
			return null;
		}
		try {
			BaseUploadFile baseUploadFile = this.doSave(upload, dealer);
			baseUploadFileService.insert(baseUploadFile);
			return baseUploadFile;
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
			throw new RuntimeException(e);
		}
	}

	// 保持文件
	private BaseUploadFile doSave(MultipartFile upload, Dealer dealer) throws IllegalStateException, IOException {

		// save photo
		String originalFilename = upload.getOriginalFilename();
		int dotIndex = originalFilename.lastIndexOf(".");
		String suffix = dotIndex == -1 ? StringUtils.EMPTY : originalFilename.substring(dotIndex + 1);

		// 文件相关
		String savepath = dealer.savepath();
		String relativeFilepath = dealer.relativeFilepath();
		String filename = dealer.filename(originalFilename, suffix);

		// 保存
		File dir = new File(savepath, relativeFilepath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir.getAbsolutePath(), filename);
		upload.transferTo(file);

		if (logger.isDebugEnabled()) {
			logger.debug("save file {}", file.getAbsolutePath());
		}

		// 访问URL
		String serverUrl = dealer.serverUrl();
		String relativeUrl = dealer.relativeUrl(relativeFilepath, filename);
		String url = dealer.getUrl(relativeFilepath, filename);

		// save file message
		BaseUploadFile uploadFile = new BaseUploadFile();
		uploadFile.setId(RandomUtils.getUUID());

		uploadFile.setFieldname(upload.getName());
		uploadFile.setOriginalFilename(originalFilename);
		uploadFile.setSize(upload.getSize());
		uploadFile.setMimetype(upload.getContentType());
		uploadFile.setFileMd5(DigestUtils.md5Hex(FileUtils.openInputStream(file)));

		uploadFile.setFileFullname(filename);
		uploadFile.setFilename(filename.lastIndexOf(".") == -1 ? filename : filename.substring(0, filename.lastIndexOf(".")));
		uploadFile.setFileSuffix(filename.lastIndexOf(".") == -1 ? "" : filename.substring(filename.lastIndexOf(".") + 1));
		uploadFile.setFileAbsolutepath(file.getAbsolutePath());

		uploadFile.setUrl(url);
		uploadFile.setServerUrl(serverUrl);
		uploadFile.setRelativeUrl(relativeUrl);

		return uploadFile;

	}

	// 处理者
	public static interface Dealer {

		// 文件存储路径,如项目目录
		String savepath();

		// 文件保存想对路径
		String relativeFilepath();

		// 文件名
		String filename(String originalFilename, String suffix);

		// 服务端访问URL
		String serverUrl();

		// 相对访问URL
		String relativeUrl(String relativeFilepath, String filename);

		// 访问URL
		String getUrl(String relativeFilepath, String filename);

	}

	// 默认处理方式
	public static class DealerAdaptor implements Dealer {

		private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		@Override
		public String savepath() {
			return ApplicationUtils.getStaticFile(ApplicationUtils.FILES).getLocation();
		}

		@Override
		public String relativeFilepath() {
			return sdf.format(new Date());
		}

		@Override
		public String filename(String originalFilename, String suffix) {
			if (StringUtils.isBlank(suffix)) {
				return RandomUtils.getUUID();
			}
			return RandomUtils.getUUID() + "." + suffix;
		}

		@Override
		public String serverUrl() {
			return ApplicationUtils.getStaticFile(ApplicationUtils.FILES).getUrl();
		}

		@Override
		public String relativeUrl(String relativeFilepath, String filename) {
			return relativeFilepath + File.separator + filename;
		}

		@Override
		public String getUrl(String relativeFilepath, String filename) {
			return ApplicationUtils.getStaticFile(ApplicationUtils.FILES).getUrl() + File.separator + relativeFilepath + File.separator + filename;
		}

	}

}
