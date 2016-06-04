package com.github.hualuomoli.raml.join.dealer;

import java.util.List;

import org.raml.model.Resource;

import com.github.hualuomoli.raml.Parser.Config;
import com.github.hualuomoli.raml.join.JoinFileDealer;
import com.github.hualuomoli.raml.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * Java - Join文件处理者
 * @author hualuomoli
 *
 */
public abstract class JavaJoinFileDealer implements JoinFileDealer {

	public static final String INDENT_CHAR = "\t";

	protected JavaConfig javaConfig;

	@Override
	public void setConfig(Config config) {
		javaConfig = (JavaConfig) config;
	}

	@Override
	public String getRelativeFilePath(Resource resource) {
		return "src/main/java/" + Tool.getPackageName(resource, javaConfig).replaceAll("[.]", "/");
	}

	@Override
	public String getFileName(Resource resource) {
		return Tool.getClassName(resource) + ".java";
	}

	@Override
	public List<String> getFileHeader(Resource resource) {

		List<String> datas = Lists.newArrayList();

		// 包名
		String packageName = Tool.getPackageName(resource, javaConfig);
		// 类名
		String className = Tool.getClassName(resource);

		// package com.github.hualuomoli.web.user;
		// add package
		datas.add("package " + packageName + ";");

		// add serializable
		datas.add("");
		// datas.add("import java.io.File;");

		datas.add("import java.io.Serializable;");
		datas.add("import java.util.Date;");

		// servlet
		datas.add("");
		datas.add("import javax.servlet.http.HttpServletRequest;");
		datas.add("import javax.servlet.http.HttpServletResponse;");

		// valid
		datas.add("");
		datas.add("import javax.validation.constraints.Max;");
		datas.add("import javax.validation.constraints.Min;");
		datas.add("import javax.validation.constraints.NotNull;");
		datas.add("import javax.validation.constraints.Pattern;");

		// hibernate
		datas.add("");
		datas.add("import org.apache.commons.lang3.builder.ToStringBuilder;");
		datas.add("import org.hibernate.validator.constraints.Length;");
		datas.add("import org.hibernate.validator.constraints.NotBlank;");
		datas.add("import org.hibernate.validator.constraints.NotEmpty;");
		datas.add("import org.springframework.format.annotation.DateTimeFormat;");
		// springframework
		datas.add("import org.slf4j.Logger;");
		datas.add("import org.slf4j.LoggerFactory;");
		datas.add("import org.springframework.stereotype.Controller;");
		datas.add("import org.springframework.ui.Model;");
		datas.add("import org.springframework.web.bind.annotation.PathVariable;");
		datas.add("import org.springframework.web.bind.annotation.RequestBody;");
		datas.add("import org.springframework.web.bind.annotation.RequestMapping;");
		datas.add("import org.springframework.web.bind.annotation.RequestMethod;");
		datas.add("import org.springframework.web.bind.annotation.RequestParam;");
		datas.add("import org.springframework.web.bind.annotation.ResponseBody;");
		datas.add("import org.springframework.web.multipart.MultipartFile;");

		// my
		datas.add("");
		datas.add("import com.github.hualuomoli.commons.json.JsonMapper;");
		datas.add("import com.github.hualuomoli.mvc.valid.EntityValidator;");

		// description
		/**
		 * @Description 登录,登出
		 * @author hualuomoli
		 */
		datas.add("");
		datas.add("/**");
		datas.add(" * @Description " + RamlUtils.dealDescription(resource.getDescription()));
		datas.add(" * @Author " + javaConfig.getAuthor());
		datas.add(" * @Date " + RamlUtils.getCurrentTime());
		datas.add(" * @Version " + javaConfig.getVersion());
		datas.add(" */");

		// @Controller(value = "com.github.hualuomoli.web.login.LoginController")
		datas.add("@Controller(value = \"" + packageName + "." + className + "\")");
		// @RequestMapping(value = "${mvc.security.auth}/user")
		datas.add("@RequestMapping(value = \"" + RamlUtils.getFullUri(resource) + "\")");

		// public class LoginController {
		// class
		datas.add("public class " + className + " {");

		datas.add("");
		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + "private static final Logger logger = LoggerFactory.getLogger(" + className + ".class);");

		return datas;
	}

	@Override
	public List<String> getFileFooter(Resource resource) {
		List<String> datas = Lists.newArrayList();
		datas.add("}");
		return datas;
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

	// tool
	static class Tool {

		/**
		 * 获取包名
		 * @param resource 资源
		 * @param javaConfig 配置
		 * @return 包名
		 */
		public static String getPackageName(Resource resource, JavaConfig javaConfig) {
			String fullUri = RamlUtils.getFullUri(resource);
			String uri = RamlUtils.removePrefix(RamlUtils.removeUriParam(fullUri), javaConfig.getIgnoreUriPrefix());
			return javaConfig.getRootPackageName() + uri.replaceAll("/", ".") + ".web";

		}

		/**
		 * 获取类名
		 * @param resource 资源
		 * @return 类名
		 */
		public static String getClassName(Resource resource) {
			String fullUri = RamlUtils.getFullUri(resource);
			return RamlUtils.cap(RamlUtils.getLastUri(fullUri)) + "Controller";
		}

		// end
	}

}
