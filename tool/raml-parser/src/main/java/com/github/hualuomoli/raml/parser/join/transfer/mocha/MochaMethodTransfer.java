package com.github.hualuomoli.raml.parser.join.transfer.mocha;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.transfer.MethodTransferAbstract;
import com.github.hualuomoli.raml.parser.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * mocha转换器
 * @author hualuomoli
 *
 */
public abstract class MochaMethodTransfer extends MethodTransferAbstract {

	public static final String TAB = "  "; // TAB

	@Override
	public String getNote(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		return "";
	}

	@Override
	public String getHeader(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		// it('没有参数', function (done) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(LINE);

		buffer.append(LINE).append(TAB);
		buffer.append("it");
		buffer.append("('");
		buffer.append(RamlUtils.dealDescription(action.getDescription()));
		buffer.append("', function (done) {");

		return buffer.toString();
	}

	@Override
	public String getContent(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		// request
		// .post('/api/uri')
		// .send({
		// })
		// .expect(200)
		// .expect(function (res) {
		// res.body.code.should.equal('0');
		// })
		// .end(done);
		StringBuilder buffer = new StringBuilder();

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

		return buffer.toString();
	}

	public abstract void addRequestParameter(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action,
			String relativeUri, String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource);

	// 获取uri,参数占位符用parentFullUriParameters替换
	private String getRequestUri(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
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

	@Override
	public String getFooter(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		// });
		StringBuilder buffer = new StringBuilder();

		buffer.append(LINE);

		buffer.append(LINE).append(TAB);
		buffer.append("});");

		return buffer.toString();
	}

	@Override
	public String getOthers(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		return "";
	}

}
