package com.github.hualuomoli.raml.join.java;

import java.util.Set;

import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.join.JoinParser;
import com.github.hualuomoli.raml.join.adaptor.util.JavaAdaptorUtils;
import com.github.hualuomoli.raml.util.RamlUtils;

/**
 * JAVA拼接字符串解析器
 * @author hualuomoli
 *
 */
public class JavaJoinParser extends JoinParser {

	protected JavaConfig javaConfig;

	@Override
	protected void setConfig(Config config) {
		super.setConfig(config);
		this.javaConfig = (JavaConfig) config;
	}

	@Override
	public String getFileHeader(String fileUri, Set<UriParameter> fileUriParameters, String fileDescription) {

		StringBuilder buffer = new StringBuilder();

		// 包名
		String uri = RamlUtils.trimPrefix(RamlUtils.trimUriParam(fileUri), javaConfig.getIgnoreUriPrefix());
		String packageName = javaConfig.getRootPackageName() + uri.replaceAll("/", ".");

		String name = JavaAdaptorUtils.cap(RamlUtils.getUriLastName(fileUri));

		// package com.github.hualuomoli.web.user;
		// add package
		buffer.append("package ").append(packageName).append(";");

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
		buffer.append(LINE).append("import com.github.hualuomoli.commons.json.JsonMapper;");
		buffer.append(LINE).append("import com.github.hualuomoli.mvc.valid.EntityValidator;");

		// description
		/**
		 * @Description 登录,登出
		 * @author hualuomoli
		 */
		buffer.append(LINE);
		buffer.append(LINE).append("/**");
		buffer.append(LINE).append(" * ").append("@Author ").append(javaConfig.getAuthor());
		buffer.append(LINE).append(" * ").append("@Date ").append(RamlUtils.getCurrentTime());
		buffer.append(LINE).append(" * ").append("@Version ").append(javaConfig.getVersion());
		buffer.append(LINE).append(" */");

		// @Controller(value = "com.github.hualuomoli.web.login.LoginController")
		buffer.append(LINE).append("@Controller(value = \"");
		// package
		buffer.append(packageName);
		// name
		buffer.append(".");
		buffer.append(name);
		buffer.append("Controller\")");

		// @RequestMapping(value = "${mvc.security.auth}/user")
		buffer.append(LINE).append("@RequestMapping(value = \"");
		buffer.append(fileUri);
		buffer.append("\")");

		// public class LoginController {
		// class
		buffer.append(LINE).append("public class ");
		buffer.append(name);
		buffer.append("Controller {");
		return buffer.toString();

	}

	@Override
	public String getFileFooter() {
		return "}";
	}

	@Override
	public String getFilePath(String fileUri) {
		String uri = RamlUtils.trimPrefix(RamlUtils.trimUriParam(fileUri), javaConfig.getIgnoreUriPrefix());
		return "src/main/java/" + javaConfig.getRootPackageName().replaceAll("[.]", "/") + uri;
	}

	@Override
	public String getFileName(String fileUri) {
		return JavaAdaptorUtils.cap(RamlUtils.getUriLastName(fileUri)) + "Controller.java";
	}

	// java 配置
	public static class JavaConfig extends Config {

		private String author; // 作者
		private double version; // 版本
		private String projectName; // 项目名称
		private String rootPackageName; // 根包名

		public JavaConfig() {
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public double getVersion() {
			return version;
		}

		public void setVersion(double version) {
			this.version = version;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public String getRootPackageName() {
			return rootPackageName;
		}

		public void setRootPackageName(String rootPackageName) {
			this.rootPackageName = rootPackageName;
		}

	}

}
