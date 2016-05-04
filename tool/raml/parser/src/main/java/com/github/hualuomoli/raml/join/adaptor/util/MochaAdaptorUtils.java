package com.github.hualuomoli.raml.join.adaptor.util;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.MimeType;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.join.AdaptorAbstract.MethodAdaptor;
import com.github.hualuomoli.raml.join.adaptor.entity.DataType;
import com.github.hualuomoli.raml.join.adaptor.entity.ParamData;
import com.github.hualuomoli.raml.join.adaptor.util.JavaAdaptorUtils.JavaTransfer;
import com.github.hualuomoli.raml.util.ParserUtils;
import com.github.hualuomoli.raml.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * 适配器工具类
 * @author hualuomoli
 *
 */
public class MochaAdaptorUtils extends AdaptorUtils implements MethodAdaptor {

	public static final String INDENT_CHAR = "  ";

	/**
	 * 获取方法的数据
	 * @param translate 转换实体类
	 * @return 方法的数据
	 */
	public String getMethodData(Translate translate) {
		return getMethodData(translate, new MochaDefaultTransfer());
	}

	/**
	 * 获取方法的数据
	 * @param translate 转换实体类
	 * @param transfer 转换器
	 * @return 方法的数据
	 */
	public String getMethodData(Translate translate, MochaTransfer transfer) {

		List<String> notes = transfer.getMethodNote(translate);
		List<String> annotations = transfer.getMethodAnnotation(translate);
		List<String> headers = transfer.getMethodHeader(translate);
		List<String> footers = transfer.getMethodFooter(translate);
		List<String> contents = transfer.getMethodContent(translate);
		List<List<String>> classDefinitionLists = transfer.getClassDefinition(translate);

		StringBuilder buffer = new StringBuilder();

		// add note
		append(buffer, notes, INDENT_CHAR, 1);

		// add annotation
		append(buffer, annotations, INDENT_CHAR, 1);

		// add header
		append(buffer, headers, INDENT_CHAR, 1);

		// add content
		append(buffer, contents, INDENT_CHAR, 2);

		// add footer
		append(buffer, footers, INDENT_CHAR, 1);

		// add class definition
		if (classDefinitionLists != null && classDefinitionLists.size() > 0) {
			for (List<String> classDefinitions : classDefinitionLists) {
				append(buffer, classDefinitions, INDENT_CHAR, 1);
			}
		}

		return buffer.toString();
	}

	// 转换器
	public static interface MochaTransfer extends Transfer {

	}

	// 默认转换器
	public static class MochaDefaultTransfer implements MochaTransfer {

		@Override
		public List<String> getMethodNote(Translate translate) {
			return Lists.newArrayList();
		}

		@Override
		public List<String> getMethodHeader(Translate translate) {
			List<String> list = Lists.newArrayList();
			// it('没有参数', function (done) {

			list.add("it('" + RamlUtils.dealDescription(translate.action.getDescription()) + "', function (done) {");

			return list;
		}

		@Override
		public List<String> getMethodFooter(Translate translate) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getMethodContent(Translate translate) {
			// request
			// .post('/api/uri')
			// .send({
			// })
			// .expect(200)
			// .expect(function (res) {
			// res.body.code.should.equal('0');
			// })
			// .end(done);
			List<String> list = Lists.newArrayList();

			list.add("request");
			String requestUri = this.getRequestUri(translate);
			list.add(INDENT_CHAR + "." + translate.action.getType().toString().toLowerCase() + "('" + requestUri + "')");
			
			
			buffer.append(LINE).append(TAB).append(TAB).append(TAB);
			buffer.append(".").append(action.getType().toString().toLowerCase()).append("('").append(realUri).append("')");
			// 增加其他信息
			this.addRequestOthers(buffer, requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource);
			// 增加参数
			this.addRequestParameter(buffer, requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource);
			// status code
			buffer.append(LINE).append(TAB).append(TAB).append(TAB);
			buffer.append(".expect(200)");

			// .expect(function (res) {
			// res.body.code.should.equal('0');
			// })
			buffer.append(LINE).append(TAB).append(TAB).append(TAB);
			buffer.append(".expect(function (res) {");

			// check
			buffer.append(LINE).append(TAB).append(TAB).append(TAB).append(TAB);
			buffer.append("res.body.should.not.be.empty();");

			buffer.append(LINE).append(TAB).append(TAB).append(TAB);
			buffer.append("})");

			// .end(done);
			buffer.append(LINE).append(TAB).append(TAB).append(TAB);
			buffer.append(".end(done)");

			buffer.append(LINE);

			return buffer.toString();
		}

		/**
		 * 获取请求的URI(参数使用例子中的数据)
		 * @param translate 转换实体类
		 * @return 请求的URI
		 */
		private String getRequestUri(Translate translate) {
			String fileUri = translate.fileUri;
			Set<UriParameter> fileUriParameters = translate.fileUriParameters;
			String[] searchList = new String[fileUriParameters.size()];
			String[] replacementList = new String[fileUriParameters.size()];

			int i = 0;
			for (UriParameter uriParameter : fileUriParameters) {
				String displayName = uriParameter.getDisplayName();
				searchList[i] = "{" + displayName + "}";
				replacementList[i] = uriParameter.getExample();
				i++;
			}

			return StringUtils.replaceEach(fileUri, searchList, replacementList);
		}
	}

}
