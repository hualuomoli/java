package com.github.hualuomoli.commons.raml.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Template Util
 * @author hualuomoli
 *
 */
public class TemplateUtils {

	private static final Logger logger = LoggerFactory.getLogger(TemplateUtils.class);
	private static final String templateEncoding = "UTF-8"; // default character encoding

	/**
	 * get template data. use default(#templateEncoding) character
	 * @param templatePath template file path
	 * @param templateName template file name
	 * @param templateEncoding template file encoding
	 * @param root create template root data. such as {"key":"value"}
	 * @return string which use data for template
	 * @throws Exception create exception
	 */
	public static String getTemplateData(String templatePath, String templateName, Object root) throws Exception {
		return getTemplateData(templatePath, templateName, templateEncoding, root);
	}

	/**
	 * get template data
	 * @param templatePath template file path
	 * @param templateName template file name
	 * @param templateEncoding template file encoding
	 * @param root create template root data. such as {"key":"value"}
	 * @return string which use data for template
	 * @throws Exception create exception
	 */
	public static String getTemplateData(String templatePath, String templateName, String templateEncoding, Object root) throws Exception {

		logger.debug("getTemplateData templatePath:{}", templatePath);
		logger.debug("getTemplateData templateName:{}", templateName);
		logger.debug("getTemplateData templateEncoding:{}", templateEncoding);
		logger.debug("getTemplateData root param:{}", ToStringBuilder.reflectionToString(root));

		/** 创建Configuration对象 */
		Configuration config = new Configuration();
		/** 指定模板路径 */
		File file = new File(templatePath);
		/** 设置要解析的模板所在的目录，并加载模板文件 */
		config.setDirectoryForTemplateLoading(file);
		/** 设置包装器，并将对象包装为数据模型 */
		config.setObjectWrapper(new DefaultObjectWrapper());
		/** 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致 */
		Template template = config.getTemplate(templateName, templateEncoding);
		/** 获取输出到文件的内容 */
		String data = getTemplateString(template, root, templatePath);

		logger.debug("template data:{}", data);
		/** 返回模板内容 */
		return data;
	}

	private static String getTemplateString(Template template, Object root, String templatePath) throws Exception {
		String id = String.valueOf(System.currentTimeMillis());
		File outFile = new File(templatePath, id);
		try {
			/** 合并数据模型与模板 */
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
			template.process(root, out);
			out.flush();
			out.close();

			/** 获取输出到文件的内容 */
			return FileUtils.readFileToString(outFile);

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			outFile.delete();
		}
	}

}
