package com.github.hualuomoli.plugin.mybatis.entity;

/**
 * 排序
 * @author hualuomoli
 *
 */
public class Order {

	private String column; // 排序的列
	private Direction direction; // 方向

	public Order(String column) {
		this.column = column;
		this.direction = Direction.ASC;
	}

	public Order(String column, Direction direction) {
		this.column = column;
		this.direction = direction;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
