package com.github.hualuomoli.raml.join.adaptor.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.join.JoinParser;
import com.github.hualuomoli.raml.join.adaptor.entity.DataType;
import com.github.hualuomoli.raml.join.adaptor.entity.ParamData;
import com.github.hualuomoli.raml.util.ParserUtils;
import com.google.common.collect.Lists;

/**
 * 适配器工具类
 * @author hualuomoli
 *
 */
public class AdaptorUtils extends ParserUtils {

	public static final String LINE = JoinParser.LINE;

	/**
	 * 获取方法名
	 * @param actionType
	 * @param methodUri
	 * @return 
	 */
	public static String getMethodName(ActionType actionType, String methodUri) {
		String type = actionType.toString().toLowerCase();

		String uri = getNoParamUri(methodUri);
		String[] array = uri.split("/");
		String name = "";
		for (int i = 0; i < array.length; i++) {
			name += cap(array[i]);
		}

		return unCap(type) + name;
	}

	/**
	 * 首字母大写
	 * @param str 需要处理的字符串
	 * @return 首字母大写
	 */
	public static String cap(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		if (str.trim().length() == 1) {
			return str;
		}
		String temp = str.trim();
		return temp.substring(0, 1).toUpperCase() + temp.substring(1);
	}

	/**
	 * 首字母小写
	 * @param str 需要处理的字符串
	 * @return 首字母小写
	 */
	public static String unCap(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		if (str.trim().length() == 1) {
			return str;
		}
		String temp = str.trim();
		return temp.substring(0, 1).toLowerCase() + temp.substring(1);
	}

	// /**
	// * 获取一行的注释
	// * @param descripion 注释
	// * @return 一行的注释
	// */
	// public static String getOneLineDescription(String descripion) {
	// if (StringUtils.isEmpty(descripion)) {
	// return StringUtils.EMPTY;
	// }
	// return descripion.replaceAll("\n", "");
	// }

	/**
	 * 转换成一行数据,如果数据为空,返回空字符串
	 * @param data 数据
	 * @return 转换后的数据
	 */
	public static String trans2OneLine(String data) {
		if (StringUtils.isEmpty(data)) {
			return StringUtils.EMPTY;
		}
		return data.replaceAll("\n", "");
	}

	/**
	 * 切分数据
	 * @param data 数据
	 * @return 切分后的数组
	 */
	public static String[] split(String data) {
		if (StringUtils.isEmpty(data)) {
			return new String[] {};
		}
		return data.split("\n");
	}

	// /**
	// * 获取一行的例子
	// * @param descripion 例子
	// * @return 一行的例子
	// */
	// public static String getOneLineExample(String example) {
	// if (StringUtils.isEmpty(example)) {
	// return StringUtils.EMPTY;
	// }
	// return example.replaceAll("\"", "\\\\\"");
	// }

	public static String translate(String data) {
		if (StringUtils.isEmpty(data)) {
			return data;
		}
		return data.replaceAll("\"", "\\\\\"");
	}

	/**
	 * 获取去掉参数的URI
	 * @param methodUri 方法的URI
	 * @return 去掉参数的URI
	 */
	public static String getNoParamUri(String methodUri) {
		if (StringUtils.isBlank(methodUri)) {
			return methodUri;
		}
		return methodUri.replaceAll("/\\{.*}", "");
	}

	/**
	 * 是否是URLEncoded协议
	 * @param mimeType 协议 
	 * @return 是否是
	 */
	public static boolean isUrlEncoded(MimeType mimeType) {
		return StringUtils.equalsIgnoreCase(mimeType.getType(), MIME_TYPE_URLENCODED);
	}

	/**
	 * 是否是JSON协议
	 * @param mimeType 协议 
	 * @return 是否是
	 */
	public static boolean isJSON(MimeType mimeType) {
		return StringUtils.equalsIgnoreCase(mimeType.getType(), MIME_TYPE_JSON);
	}

	/**
	* 是否是XML协议
	* @param mimeType 协议 
	* @return 是否是
	*/
	public static boolean isXML(MimeType mimeType) {
		return StringUtils.equalsIgnoreCase(mimeType.getType(), MIME_TYPE_XML);
	}

	/**
	* 是否是MultiPart协议
	* @param mimeType 协议 
	* @return 是否是
	*/
	public static boolean isMultiPart(MimeType mimeType) {
		return StringUtils.equalsIgnoreCase(mimeType.getType(), MIME_TYPE_MULTIPART);
	}

	// 添加
	public static void append(StringBuilder buffer, List<String> datas, String indentChar, int level) {
		if (datas == null || datas.size() == 0) {
			return;
		}
		String t = "";
		for (int i = 0; i < level; i++) {
			t += indentChar;
		}
		for (String data : datas) {
			buffer.append(LINE).append(t).append(data);
		}
	}

	/**
	 * 解析参数
	 * @param params 参数
	 * @return 参数数据
	 */
	public static List<ParamData> parseParams(Collection<AbstractParam> params) {
		List<ParamData> paramDatas = Lists.newArrayList();
		if (params == null || params.size() == 0) {
			return paramDatas;
		}
		for (AbstractParam param : params) {
			ParamData paramData = new ParamData(param);
			paramData.setDataType(DataType.SIMPLE);
			paramDatas.add(paramData);
		}
		return paramDatas;
	}

	// 转换器
	public static interface Transfer {

		// 方法注释
		List<String> getMethodNote(Translate translate);

		// 方法头部
		List<String> getMethodHeader(Translate translate);

		// 方法尾部
		List<String> getMethodFooter(Translate translate);

		// 方法内容
		List<String> getMethodContent(Translate translate);

	}

	// 转换
	public static class Translate {
		public ActionType actionType;
		public MimeType formMimeType;
		public ResponseSM responseSM;
		public Action action;
		public String methodUri;
		public Set<UriParameter> methodUriParameters;
		public String fileUri;
		public Set<UriParameter> fileUriParameters;

		public Translate() {
		}

		public Translate(ActionType actionType, MimeType formMimeType, ResponseSM responseSM, Action action, String methodUri,
				Set<UriParameter> methodUriParameters, String fileUri, Set<UriParameter> fileUriParameters) {
			this.actionType = actionType;
			this.formMimeType = formMimeType;
			this.responseSM = responseSM;
			this.action = action;
			this.methodUri = methodUri;
			this.methodUriParameters = methodUriParameters;
			this.fileUri = fileUri;
			this.fileUriParameters = fileUriParameters;
		}

	}

}
