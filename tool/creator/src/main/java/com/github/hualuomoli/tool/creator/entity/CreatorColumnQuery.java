package com.github.hualuomoli.tool.creator.entity;

/**
 * 实体类属性
 * @author hualuomoli
 *
 */
public class CreatorColumnQuery extends CreatorColumn {

	private String operator;
	private boolean array; // 是否是Array
	private boolean like; // 是否是like
	private String likeJavaName; // like的名字

	public CreatorColumnQuery() {
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public boolean isArray() {
		return array;
	}

	public void setArray(boolean array) {
		this.array = array;
	}

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public String getLikeJavaName() {
		return likeJavaName;
	}

	public void setLikeJavaName(String likeJavaName) {
		this.likeJavaName = likeJavaName;
	}

}
