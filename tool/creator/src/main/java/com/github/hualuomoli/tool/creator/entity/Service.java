package com.github.hualuomoli.tool.creator.entity;

/**
 * 实体类Service
 * @author hualuomoli
 *
 */
public class Service {

	private Mapper mapper; // mapper
	private Entity entity;// entity
	private String name; // 名称 entity + Service
	private String packageName; // 包名
	private String fullName; // 全名称(包名.类名)
	private String filename; // 文件名 entity + Service.java
	private String filepath; // 文件路径

	public Service() {
	}

	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
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

}
