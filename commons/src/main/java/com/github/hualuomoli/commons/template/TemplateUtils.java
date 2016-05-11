package com.github.hualuomoli.commons.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.template.exception.TemplateException;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 模板工具类
 * @author hualuomoli
 *
 */
public class TemplateUtils {

	private static final Logger logger = LoggerFactory.getLogger(TemplateUtils.class);
	private static final String DEFAULT_ENCODING = "UTF-8"; // 默认UTF-8

	/**
	 * 获取资源的绝对路径
	 * @param resourcePath 资源文件
	 * @return 绝对路径
	 */
	public static String getAbsolutePath(String resourcePath) {
		return TemplateUtils.class.getClassLoader().getResource(resourcePath).getPath();

	}

	/**
	 * 执行输出
	 * @param templateAbsolutePath 模板文件绝对路径
	 * @param templateName 模板名称
	 * @param data 数据
	 * @param output 输出文件
	 */
	public static void processByResource(String templateResourcePath, String templateName, Object data, File output) {
		processByResource(templateResourcePath, templateName, DEFAULT_ENCODING, data, output);
	}

	/**
	 * 执行输出
	 * @param templateAbsolutePath 模板文件绝对路径
	 * @param templateName 模板名称
	 * @param templateEncoding 模板编码
	 * @param data 数据
	 * @param output 输出文件
	 */
	public static void processByResource(String templateResourcePath, String templateName, String templateEncoding, Object data, File output) {
		process(getAbsolutePath(templateResourcePath), templateName, templateEncoding, data, output);
	}

	/**
	 * 执行输出
	 * @param templateAbsolutePath 模板文件绝对路径
	 * @param templateName 模板名称
	 * @param data 数据
	 * @param output 输出文件
	 */
	public static void process(String templateAbsolutePath, String templateName, Object data, File output) {
		process(templateAbsolutePath, templateName, DEFAULT_ENCODING, data, output);
	}

	/**
	 * 执行输出
	 * @param templateAbsolutePath 模板文件绝对路径
	 * @param templateName 模板名称
	 * @param templateEncoding 模板编码
	 * @param data 数据
	 * @param output 输出文件
	 */
	public static void process(String templateAbsolutePath, String templateName, String templateEncoding, Object data, File output) {

		if (logger.isDebugEnabled()) {
			logger.debug("templateAbsolutePath {}", templateAbsolutePath);
			logger.debug("templateName {}", templateName);
			logger.debug("templateEncoding {}", templateEncoding);
			logger.debug("data {} ", ToStringBuilder.reflectionToString(data));
			logger.debug("output {} ", output);
		}

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
			Template template = config.getTemplate(templateName, templateEncoding);
			/** 输出到文件 */
			process(template, data, output);
		} catch (Exception e) {
			throw new TemplateException(e);
		}

	}

	/**
	 * 执行输出
	 * @param template 模板
	 * @param data 数据
	 * @param output 输出文件
	 */
	public static void process(Template template, Object data, File output) {

		// 如果父路径不存在,创建
		File parentFile = output.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}

		Writer out = null;
		try {
			/** 合并数据模型与模板 */
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
			template.process(data, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			try {
				// 关闭连接,如果不关闭,文件不能删除
				out.close();
				out = null;
				// 删除临时文件
				if (output.exists()) {
					FileUtils.forceDelete(output);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new TemplateException(e);
		}
	}

}
