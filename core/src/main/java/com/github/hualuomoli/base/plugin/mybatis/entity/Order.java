package com.github.hualuomoli.base.plugin.mybatis.entity;

/**
 * 排序
 * @author hualuomoli
 *
 */
public class Order {

	public static final Order CREATE_DATE_DESC = new Order("create_date", Direction.DESC);
	public static final Order CREATE_DATE_ASC = new Order("create_date", Direction.ASC);
	public static final Order UPDATE_DATE_DESC = new Order("update_date", Direction.DESC);
	public static final Order UPDATE_DATE_ASC = new Order("update_date", Direction.ASC);
	public static final Order TREE_SORT_DESC = new Order("sort", Direction.DESC);
	public static final Order TREE_SORT_ASC = new Order("sort", Direction.ASC);

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
