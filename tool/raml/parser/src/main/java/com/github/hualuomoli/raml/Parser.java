package com.github.hualuomoli.raml;

import java.nio.charset.Charset;

/**
 * 解析器
 * @author hualuomoli
 *
 */
public interface Parser {

	/**
	 * 初始化
	 * @param config 配置信息
	 */
	void init(Config config);

	/**
	 * 执行解析
	 * @param ramlResources Raml 文件的资源路径,文件必须放在资源目录下
	 */
	void execute(String[] ramlResources);

	/**
	 * 配置
	 * @author hualuomoli
	 *
	 */
	public static class Config {

		private String outputFilepath; // 输出目录
		private boolean clear; // 清除输出目录
		private Charset encoding; // 文件编码
		private String ignoreUriPrefix; // 忽略的uri前缀,如 /api

		public String getOutputFilepath() {
			return outputFilepath;
		}

		public void setOutputFilepath(String outputFilepath) {
			this.outputFilepath = outputFilepath;
		}

		public boolean isClear() {
			return clear;
		}

		public void setClear(boolean clear) {
			this.clear = clear;
		}

		public Charset getEncoding() {
			return encoding;
		}

		public void setEncoding(Charset encoding) {
			this.encoding = encoding;
		}

		public String getIgnoreUriPrefix() {
			return ignoreUriPrefix;
		}

		public void setIgnoreUriPrefix(String ignoreUriPrefix) {
			this.ignoreUriPrefix = ignoreUriPrefix;
		}

	}

}
