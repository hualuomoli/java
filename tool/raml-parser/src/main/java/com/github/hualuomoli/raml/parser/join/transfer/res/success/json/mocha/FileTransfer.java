package com.github.hualuomoli.raml.parser.join.transfer.res.success.json.mocha;

import java.util.List;
import java.util.Map;

import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.ParamType;
import org.raml.model.Resource;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.transfer.util.TransferUtils;

/**
 * 请求参数为multipart/form-data
 * @author hualuomoli
 *
 */
public class FileTransfer extends MochaTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) throws ParseException {
		return TransferUtils.isFile(action, requestMimeType);
	}

	@Override
	public void addRequestParameter(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		// .field('token', '1234567890')
		// .attach('photo', path.join(__dirname, '../../favicon.ico'))
		Map<String, List<FormParameter>> formParameters = requestMimeType.getFormParameters();
		for (String displayName : formParameters.keySet()) {
			List<FormParameter> list = formParameters.get(displayName);
			for (FormParameter formParameter : list) {
				if (formParameter.getType() == ParamType.FILE) {
					buffer.append(LINE).append(TAB).append(TAB).append(TAB);
					buffer.append(".attach(");
					// key
					buffer.append("'");
					buffer.append(formParameter.getDisplayName());
					buffer.append("', path.join(__dirname, '../../favicon.ico')");

					buffer.append(")");
				} else {
					buffer.append(LINE).append(TAB).append(TAB).append(TAB);
					buffer.append(".field(");
					// key
					buffer.append("'");
					buffer.append(formParameter.getDisplayName());
					buffer.append("', '");
					buffer.append(formParameter.getExample());
					buffer.append("'");

					buffer.append(")");

				}
			}
		}
	}

}
