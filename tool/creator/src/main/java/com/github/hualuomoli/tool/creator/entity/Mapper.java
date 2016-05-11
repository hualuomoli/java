package com.github.hualuomoli.tool.creator.entity;

/**
 * 实体类Mapper
 * @author hualuomoli
 *
 */
public class Mapper {

	private Entity entity; // 实体类
	private String name; // 名称 entity + Mapper
	private String packageName; // 包名
	private String fullName; // 全名称(包名.类名)
	private String filename; // 文件名 entity + Mapper.java
	private String filepath; // 文件路径

	private String configFileName; // 配置文件的文件名 entity + Mapper.xml
	private String configFilePath; // 配置文件的文件路径

	public Mapper() {
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getConfigFileName() {
		return configFileName;
	}

	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

}
