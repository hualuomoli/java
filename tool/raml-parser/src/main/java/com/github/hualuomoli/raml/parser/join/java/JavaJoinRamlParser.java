package com.github.hualuomoli.raml.parser.join.java;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.JoinRamlParser;
import com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.get.GetTransfer;
import com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.json.JsonJsonTransfer;
import com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.multipart.MultipartJsonTransfer;
import com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.raml.RamlJsonTransfer;
import com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.urlencoded.UrlEncodedJsonTransfer;
import com.github.hualuomoli.raml.parser.join.transfer.Transfer;
import com.github.hualuomoli.raml.parser.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * java解析
 * @author hualuomoli
 *
 */
public class JavaJoinRamlParser extends JoinRamlParser {

	protected String packageName = "com.github.hualuomoli"; // 包名
	protected String uriPrefix; // URI的前缀,如 /api/user 希望目录不包含api
	protected String author = "hualuomoli"; // 作者,用于注释

	public JavaJoinRamlParser() {
		super();
		List<Transfer> transferList = Lists.newArrayList();
		transferList.add(new GetTransfer());
		transferList.add(new RamlJsonTransfer());
		transferList.add(new UrlEncodedJsonTransfer());
		transferList.add(new JsonJsonTransfer());
		transferList.add(new MultipartJsonTransfer());
		this.setTransferList(transferList);
	}

	public JavaJoinRamlParser(List<Transfer> transferList) {
		super(transferList);
	}

	@Override
	public String getFileHeader(List<String> actionDatas, String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource)
			throws ParseException {
		StringBuilder buffer = new StringBuilder();

		// package com.github.hualuomoli.web.user;
		// add package
		buffer.append("package ").append(this.getFullPackage(parentFullUri)).append(";");

		// add serializable
		buffer.append(LINE);
		buffer.append(LINE).append("import java.io.Serializable;");

		// servlet
		buffer.append(LINE);
		buffer.append(LINE).append("import javax.servlet.http.HttpServletRequest;");
		buffer.append(LINE).append("import javax.servlet.http.HttpServletResponse;");

		// springframework
		buffer.append(LINE);
		buffer.append(LINE).append("import org.springframework.stereotype.Controller;");
		buffer.append(LINE).append("import org.springframework.ui.Model;");
		buffer.append(LINE).append("import org.springframework.web.bind.annotation.RequestBody;");
		buffer.append(LINE).append("import org.springframework.web.bind.annotation.RequestMapping;");
		buffer.append(LINE).append("import org.springframework.web.bind.annotation.RequestMethod;");
		buffer.append(LINE).append("import org.springframework.web.bind.annotation.ResponseBody;");

		// description
		/**
		 * @Description 登录,登出
		 * @author hualuomoli
		 */
		buffer.append(LINE);
		buffer.append(LINE).append("/**");
		buffer.append(LINE).append(" * ").append("@Description ");
		List<String> lines = RamlUtils.splitByLine(resource.getDescription());
		if (lines.size() == 1) {
			buffer.append(lines.get(0));
		} else {
			for (String line : lines) {
				buffer.append(LINE).append(" * ").append("             ").append(line);
			}
		}
		buffer.append(LINE).append(" * ").append("@Author ").append(author);
		buffer.append(LINE).append(" * ").append("@Date ").append(RamlUtils.getCurrentTime());
		buffer.append(LINE).append(" * ").append("@Version ").append(version);
		buffer.append(LINE).append(" */");

		// @Controller(value = "com.github.hualuomoli.web.login.LoginController")
		buffer.append(LINE).append("@Controller");
		buffer.append("(").append("value").append(" = ");
		buffer.append(QUOTES);
		// package
		buffer.append(this.getFullPackage(parentFullUri));
		// name
		buffer.append(".");
		buffer.append(RamlUtils.getUriLastName(parentFullUri));
		buffer.append("Controller");

		buffer.append(QUOTES);
		buffer.append(")");

		// @RequestMapping(value = "${mvc.security.auth}/user")
		buffer.append(LINE).append("@RequestMapping");
		buffer.append("(");
		buffer.append("value").append(" = ");
		buffer.append(QUOTES);
		buffer.append(parentFullUri);
		buffer.append(QUOTES);
		buffer.append(")");

		// public class LoginController {
		// class
		buffer.append(LINE).append("public class ");
		buffer.append(RamlUtils.getUriLastName(parentFullUri));
		buffer.append("Controller {");
		return buffer.toString();
	}

	@Override
	public String getFileFooter(List<String> actionDatas, String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource)
			throws ParseException {
		return new StringBuilder().append(LINE).append("}").toString();
	}

	@Override
	public String getFilepath() throws ParseException {
		return "src/main/java";
	}

	@Override
	public String getFilename(String parentFullUri, Resource resource) throws ParseException {
		StringBuilder buffer = new StringBuilder();
		// package
		buffer.append(this.getFullPackage(parentFullUri));
		// name
		buffer.append(".");
		buffer.append(RamlUtils.getUriLastName(parentFullUri));
		buffer.append("Controller");
		return buffer.toString().replaceAll("[.]", "/") + ".java";
	}

	@Override
	public void configFile(Map<ActionType, Action> actions, Map<String, Resource> noChildResources, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		// TODO Auto-generated method stub

	}

	/**
	 * 获取全包名
	 * @param parentFullUri URI
	 * @return 包名
	 */
	protected String getFullPackage(String parentFullUri) {
		StringBuilder buffer = new StringBuilder();
		// package
		buffer.append(packageName);

		// uri path
		String uriPath = RamlUtils.trimUriParam(parentFullUri);
		if (StringUtils.isEmpty(uriPrefix)) {
			// uri prefix is null
			buffer.append(uriPath);
		} else if (uriPath.startsWith(uriPrefix)) {
			// start with uri prefix
			buffer.append(uriPath.substring(uriPrefix.length()));
		} else {
			// not start with uri prefix
			buffer.append(uriPath);
		}

		// add to web folder
		buffer.append(".web");

		return buffer.toString().replaceAll("/", ".");
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setUriPrefix(String uriPrefix) {
		this.uriPrefix = uriPrefix;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
