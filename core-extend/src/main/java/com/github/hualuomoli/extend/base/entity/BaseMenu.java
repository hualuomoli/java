package com.github.hualuomoli.extend.base.entity;

import org.apache.commons.lang3.StringUtils;

public class BaseMenu extends com.github.hualuomoli.extend.entity.Menu
 implements com.github.hualuomoli.base.persistent.BasePersistent, com.github.hualuomoli.base.persistent.CommonPersistent
 {

	/**  - 查询数组 */
	private java.lang.String[] menuCodeArray;
	
	public BaseMenu(){
	}
	
	public BaseMenu(
		java.lang.String menuCode
	){
		this.setMenuCode(menuCode);
	}
	
	public java.lang.String[] getMenuCodeArray() {
		return menuCodeArray;
	}
	
	public void setMenuCodeArray(java.lang.String[] menuCodeArray) {
		if (menuCodeArray == null || menuCodeArray.length == 0) {
			return;
		}
		this.menuCodeArray = menuCodeArray;
	}

}
