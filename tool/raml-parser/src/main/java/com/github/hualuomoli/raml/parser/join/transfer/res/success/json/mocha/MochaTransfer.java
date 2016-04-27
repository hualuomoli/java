package com.github.hualuomoli.raml.parser.join.transfer.res.success.json.mocha;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.Join;
import com.github.hualuomoli.raml.parser.join.transfer.Transfer;
import com.github.hualuomoli.raml.parser.join.transfer.res.success.json.ResponseSuccessJsonTransfer;
import com.google.common.collect.Lists;

/**
 * mocha转换器
 * @author hualuomoli
 *
 */
public abstract class MochaTransfer extends ResponseSuccessJsonTransfer implements Join, Transfer {

	public static final String TAB = "  "; // TAB

	@Override
	public String getData(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {

		// it('没有参数', function (done) {
		//
		// request
		// .get('/api/uri')
		// .expect(200)
		// .expect(function (res) {
		// res.body.code.should.equal('0');
		// })
		// .end(done);
		//
		// });
		StringBuilder buffer = new StringBuilder();

		// header
		this.addHeader(buffer, requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource);

		// request
		this.addRequest(buffer, requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource);

		// footer
		this.addFooter(buffer, requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource);

		return buffer.toString();
	}

	/**
	 * 设置方法的header
	 * @param buffer buffer
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 */
	private void addHeader(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		// it('没有参数', function (done) {

		buffer.append(LINE);

		buffer.append(LINE).append(TAB);
		buffer.append("it");
		buffer.append("('");
		buffer.append(action.getDescription().replaceAll("\\n", "    "));
		buffer.append("', function (done) {");

	}

	/**
	 * 设置方法的footer
	 * @param buffer buffer
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 */
	private void addFooter(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		// });

		buffer.append(LINE);

		buffer.append(LINE).append(TAB);
		buffer.append("});");

	}

	/**
	 * 设置方法的request
	 * @param buffer buffer
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUri 父URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 */
	public void addRequest(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		// request
		// .post('/api/uri')
		// .send({
		// })
		// .expect(200)
		// .expect(function (res) {
		// res.body.code.should.equal('0');
		// })
		// .end(done);

		String realUri = this.getRequestUri(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource);

		buffer.append(LINE);

		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("request");

		buffer.append(LINE).append(TAB).append(TAB).append(TAB);
		buffer.append(".").append(action.getType().toString().toLowerCase()).append("('").append(realUri).append("')");
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
	}

	/**
	 * 设置方法的request的参数
	 * @param buffer buffer
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUri 父URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 */
	public abstract void addRequestParameter(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action,
			String relativeUri, String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource);

	/**
	 * 获取请求的URI,使用example中的数据替换uri的占位符
	 * @param buffer buffer
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUri 父URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return 请求的URI,使用example中的数据替换uri的占位符
	 */
	protected String getRequestUri(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {

		String uri = parentFullUri + relativeUri;
		List<String> searchList = Lists.newArrayList();
		List<String> replacementList = Lists.newArrayList();

		if (parentFullUriParameters != null && parentFullUriParameters.size() > 0) {
			for (UriParameter uriParameter : parentFullUriParameters.values()) {
				searchList.add("{" + uriParameter.getDisplayName() + "}");
				replacementList.add(uriParameter.getExample() == null ? "123456" : uriParameter.getExample());
			}
		}
		String realUri = StringUtils.replaceEach(uri, searchList.toArray(new String[] {}), replacementList.toArray(new String[] {}));

		return realUri;

	}

}
