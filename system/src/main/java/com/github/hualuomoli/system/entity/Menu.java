package com.github.hualuomoli.system.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityColumnQuery;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.CommonField;
import com.github.hualuomoli.extend.Tree;
import com.github.hualuomoli.extend.notice.Noticer;

/**
 * 菜单
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
@EntityTable(comment = "菜单", unique = { "menuCode" })
public class Menu extends CommonField implements Tree, Noticer {

	@EntityColumnQuery(inArray = true)
	private String menuCode;
	private String menuName;
	private String parentCode;
	private String fullName;
	@EntityColumn(comment = "菜单类型 1=菜单,2=权限")
	private Integer menuType;
	@EntityColumn(comment = "图标", length = 200)
	private String icon;
	@EntityColumn(comment = "菜单路由状态", length = 100)
	private String routerState;
	@EntityColumn(comment = "权限字符串", length = 32)
	private String permission; // 权限字符串
	private Integer menuSort;

	public Menu() {
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRouterState() {
		return routerState;
	}

	public void setRouterState(String routerState) {
		this.routerState = routerState;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}

	@Override
	public void setId(String id) {
		super.setId(id);
	}

	@Override
	public String getDataCode() {
		return this.menuCode;
	}

	@Override
	public void setDataCode(String dataCode) {
		menuCode = dataCode;
	}

	@Override
	public String getDataName() {
		return this.menuName;
	}

	@Override
	public void setDataName(String dataName) {
		menuName = dataName;
	}

	@Override
	public Integer getDataSort() {
		return this.menuSort;
	}

	@Override
	public void setDataSort(Integer dataSort) {
		menuSort = dataSort;
	}

	@Override
	public String getParentCode() {
		return this.parentCode;
	}

	@Override
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	@Override
	public String getFullName() {
		return this.fullName;
	}

	@Override
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String getDataSortName() {
		return "menu_sort";
	}

	@Override
	public String getNameSeparator() {
		return "/";
	}

}
