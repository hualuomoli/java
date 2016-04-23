package com.github.hualuomoli.raml.parser.java;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.JoinRamlParser;
import com.github.hualuomoli.raml.parser.RamlParserAbstract;
import com.github.hualuomoli.raml.parser.exception.ParseException;

/**
 * java解析
 * @author hualuomoli
 *
 */
public class JavaRamlParser extends JoinRamlParser {

	private String packageName = "com.github.hualuomoli"; // 包名
	private String uriPrefix; // URI的前缀,如 /api/user 希望目录不包含api
	private String author = "hualuomoli"; // 作者,用于注释

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
		List<String> lines = RamlParserAbstract.splitByLine(resource.getDescription());
		if (lines.size() == 1) {
			buffer.append(lines.get(0));
		} else {
			for (String line : lines) {
				buffer.append(LINE).append(" * ").append("             ").append(line);
			}
		}
		buffer.append(LINE).append(" * ").append("@Author ").append(this.getAuthor());
		buffer.append(LINE).append(" * ").append("@Date ").append(this.getCurrentTime());
		buffer.append(LINE).append(" * ").append("@Version ").append(this.getVersion());
		buffer.append(LINE).append(" */");

		// @Controller(value = "com.github.hualuomoli.web.login.LoginController")
		buffer.append(LINE).append("@Controller");
		buffer.append("(").append("value").append(" = ");
		buffer.append(QUOTES);
		// package
		buffer.append(this.getFullPackage(parentFullUri));
		// name
		buffer.append(".");
		buffer.append(this.getUriLastName(parentFullUri));
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
		buffer.append(this.getUriLastName(parentFullUri));
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
		buffer.append(this.getUriLastName(parentFullUri));
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
		buffer.append(this.getPackageName());

		// uri path
		String uriPath = this.trimUriParam(parentFullUri);
		if (StringUtils.isEmpty(this.getUriPrefix())) {
			// uri prefix is null
			buffer.append(uriPath);
		} else if (uriPath.startsWith(this.getUriPrefix())) {
			// start with uri prefix
			buffer.append(uriPath.substring(this.getUriPrefix().length()));
		} else {
			// not start with uri prefix
			buffer.append(uriPath);
		}

		// add to web folder
		buffer.append(".web");

		return buffer.toString().replaceAll("/", ".");
	}

	/**
	 * 获取URI最后一个名称 /user/order/product --> product
	 * @param parentFullUri
	 * @return 最后一个URI名称
	 */
	private String getUriLastName(String parentFullUri) {
		String uriPath = this.trimUriParam(parentFullUri);
		if (StringUtils.isEmpty(uriPath)) {
			return StringUtils.EMPTY;
		}
		String[] array = uriPath.split("/");
		String name = array[array.length - 1];
		return name;
	}

	/**
	 * 去掉URI中参数部分 /user/{username}/{addressid} --> /user
	 * @param uri URI
	 * @return 去掉URI中参数部分
	 */
	private String trimUriParam(String uri) {
		if (StringUtils.isEmpty(uri)) {
			return StringUtils.EMPTY;
		}
		return uri.replaceAll("/\\{.*}", "");
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getUriPrefix() {
		return uriPrefix;
	}

	public void setUriPrefix(String uriPrefix) {
		this.uriPrefix = uriPrefix;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
