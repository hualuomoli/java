package com.github.hualuomoli.commons.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.constant.Charset;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 模板工具类
 * @author hualuomoli
 *
 */
public class TemplateUtils {

	private static final Logger logger = LoggerFactory.getLogger(TemplateUtils.class);

	private static final String DEFAULT_ENCODING = Charset.UTF8.name();

	/**
	 * 执行输出
	 * @param templateResourcePath 模板文件资源路径
	 * @param templateName 模板名称
	 * @param data 数据
	 * @param output 输出文件
	 */
	public static void processByResource(String templateResourcePath, String templateName, Object data, File output) {
		processByResource(templateResourcePath, templateName, data, output, DEFAULT_ENCODING);
	}

	/**
	 * 执行输出
	 * @param templateResourcePath 模板文件资源路径
	 * @param templateName 模板名称
	 * @param data 数据
	 * @param output 输出文件
	 * @param templateEncoding 模板编码
	 */
	public static void processByResource(String templateResourcePath, String templateName, Object data, File output, String templateEncoding) {
		if (logger.isDebugEnabled()) {
			logger.debug("templateResourcePath {}", templateResourcePath);
			logger.debug("templateName {}", templateName);
			logger.debug("data {} ", ToStringBuilder.reflectionToString(data));
			logger.debug("output {} ", output);
			logger.debug("templateEncoding {}", templateEncoding);
		}

		try {
			Template template = getResourceTemplate(templateResourcePath, templateName, templateEncoding);
			_flush(template, data, output);
		} catch (TemplateException e) {
			logger.error("{}", e);
		}
	}

	/**
	 * 执行输出
	 * @param templateAbsolutePath 模板文件绝对路径
	 * @param templateName 模板名称
	 * @param data 数据
	 * @param output 输出文件
	 */
	public static void process(String templateAbsolutePath, String templateName, Object data, File output) {
		process(templateAbsolutePath, templateName, data, output, DEFAULT_ENCODING);
	}

	/**
	 * 执行输出
	 * @param templateAbsolutePath 模板文件绝对路径
	 * @param templateName 模板名称
	 * @param data 数据
	 * @param output 输出文件
	 * @param templateEncoding 模板编码
	 */
	public static void process(String templateAbsolutePath, String templateName, Object data, File output, String templateEncoding) {

		if (logger.isDebugEnabled()) {
			logger.debug("templateAbsolutePath {}", templateAbsolutePath);
			logger.debug("templateName {}", templateName);
			logger.debug("data {} ", ToStringBuilder.reflectionToString(data));
			logger.debug("output {} ", output);
			logger.debug("templateEncoding {}", templateEncoding);
		}

		try {
			Template template = getTemplate(templateAbsolutePath, templateName, templateEncoding);
			_flush(template, data, output);
		} catch (TemplateException e) {
			logger.error("{}", e);
		}

	}

	// 输出
	private static void _flush(Template template, Object data, File output) throws TemplateException {
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
			template.process(data, out);
			out.flush();
		} catch (TemplateException e) {
			throw e;
		} catch (IOException e) {
			logger.warn("{}", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 获取模板
	 * @param templateResourcePath 模板资源路径
	 * @param templateName 模板文件
	 * @param templateEncoding 模板编码集
	 * @return 模板
	 */
	public static Template getResourceTemplate(String templateResourcePath, String templateName, String templateEncoding) {
		File temp = null;
		InputStream is = null;
		OutputStream os = null;
		try {
			String id = RandomUtils.getString();
			String projectPath = ProjectUtils.getProjectPath();
			temp = new File(projectPath, id);
			is = TemplateUtils.class.getClassLoader().getResourceAsStream(templateResourcePath + "/" + templateName);
			os = new FileOutputStream(temp);
			IOUtils.copy(is, os);
			return getTemplate(projectPath, id, templateEncoding);
		} catch (IOException e) {
			// 不可能存在
			logger.error("find error {}", e);
			return null;
		} finally {
			// 关闭输入流
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			// 关闭输出流
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
			// 删除文件
			if (temp != null) {
				try {
					FileUtils.forceDelete(temp);
				} catch (IOException e) {
				}
			}
		}

	}

	/**
	 * 获取模板
	 * @param templateAbsolutePath 模板绝对路径
	 * @param templateName 模板文件
	 * @param templateEncoding 模板编码集
	 * @return 模板
	 */
	public static Template getTemplate(String templateAbsolutePath, String templateName, String templateEncoding) {
		try {
			/** 创建Configuration对象 */
			Configuration config = new Configuration();
			/** 指定模板路径 */
			File file = new File(templateAbsolutePath);
			/** 设置要解析的模板所在的目录，并加载模板文件 */
			config.setDirectoryForTemplateLoading(file);
			/** 设置包装器，并将对象包装为数据模型 */
			config.setObjectWrapper(new DefaultObjectWrapper());
			/** 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致 */
			return config.getTemplate(templateName, templateEncoding);
		} catch (IOException e) {
			logger.error("{}", e);
			return null;
		}
	}

}
