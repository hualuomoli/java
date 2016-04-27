package com.github.hualuomoli.raml.parser.join.java;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.JoinRamlParser;
import com.github.hualuomoli.raml.parser.join.transfer.Transfer;
import com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java.FileTransfer;
import com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java.GetTransfer;
import com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java.JsonTransfer;
import com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java.RestfulTransfer;
import com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java.UrlEncodedTransfer;
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
		// restfule
		transferList.add(new RestfulTransfer());
		// get
		transferList.add(new GetTransfer());
		// UrlEncoded
		transferList.add(new UrlEncodedTransfer());
		// JSON
		transferList.add(new JsonTransfer());
		// file
		transferList.add(new FileTransfer());
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
		// buffer.append(LINE).append("import java.io.File;");
		buffer.append(LINE).append("import java.io.Serializable;");
		buffer.append(LINE).append("import java.util.Date;");

		// servlet
		buffer.append(LINE);
		buffer.append(LINE).append("import javax.servlet.http.HttpServletRequest;");
		buffer.append(LINE).append("import javax.servlet.http.HttpServletResponse;");

		// valid
		buffer.append(LINE).append("import javax.validation.constraints.Max;");
		buffer.append(LINE).append("import javax.validation.constraints.Min;");
		buffer.append(LINE).append("import javax.validation.constraints.NotNull;");
		buffer.append(LINE).append("import javax.validation.constraints.Pattern;");

		// hibernate valid
		buffer.append(LINE).append("import org.hibernate.validator.constraints.Length;");
		buffer.append(LINE).append("import org.hibernate.validator.constraints.NotEmpty;");
		buffer.append(LINE).append("import org.springframework.format.annotation.DateTimeFormat;");

		// springframework
		buffer.append(LINE).append("import org.springframework.stereotype.Controller;");
		buffer.append(LINE).append("import org.springframework.ui.Model;");
		buffer.append(LINE).append("import org.springframework.web.bind.annotation.PathVariable;");
		buffer.append(LINE).append("import org.springframework.web.bind.annotation.RequestBody;");
		buffer.append(LINE).append("import org.springframework.web.bind.annotation.RequestMapping;");
		buffer.append(LINE).append("import org.springframework.web.bind.annotation.RequestMethod;");
		buffer.append(LINE).append("import org.springframework.web.bind.annotation.RequestParam;");
		buffer.append(LINE).append("import org.springframework.web.bind.annotation.ResponseBody;");
		buffer.append(LINE).append("import org.springframework.web.multipart.MultipartFile;");

		// my
		buffer.append(LINE);
		buffer.append(LINE).append("import com.github.hualuomoli.mvc.valid.EntityValidator;");

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

	@Override
	protected void configProejct(Raml raml) {
		try {
			// use web project
			String path = JavaJoinRamlParser.class.getClassLoader().getResource("log4j.properties").getPath();
			// root project path
			String rootProjectFilepath = path.substring(0, path.indexOf("/tool/raml-parser/target"));
			// web project path
			String webProjectFilepath = new File(rootProjectFilepath, "web").getAbsolutePath();

			// filename folder
			String folder;
			String filename;

			// pom.xml
			filename = "pom.xml";
			String pomData = FileUtils.readFileToString(new File(webProjectFilepath, filename), "UTF-8");
			// update <artifactId>web</artifactId>
			pomData = StringUtils.replace(pomData, "<artifactId>web</artifactId>", "<artifactId>web-demo</artifactId>");
			// update <warName>web</warName>
			pomData = StringUtils.replace(pomData, "<warName>web</warName>", "<warName>web-demo</warName>");
			// flush
			FileUtils.write(new File(outputFilepath, filename), pomData, "UTF-8");

			// .gitignore
			filename = ".gitignore";
			FileUtils.copyFile(new File(webProjectFilepath, filename), new File(outputFilepath, filename));

			// src
			folder = "src";
			FileUtils.copyDirectory(new File(webProjectFilepath, folder), new File(outputFilepath, folder));

		} catch (Exception e) {
		}
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
