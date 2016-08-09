package com.github.hualuomoli.system.menu.web;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.hualuomoli.mvc.annotation.RequestVersion;
import com.github.hualuomoli.mvc.rest.AppRestResponse;
import com.github.hualuomoli.mvc.validator.EntityValidator;
import com.github.hualuomoli.system.base.entity.BaseMenu;
import com.github.hualuomoli.system.menu.service.MenuService;

@RequestVersion
@RequestMapping(value = "/system/menu")
@RestController(value = "com.github.hualuomoli.system.menu.web.MenuController")
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * 获取所有的有效菜单
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = { "application/json" })
	public String getAll() {

		List<BaseMenu> list = menuService.getAll();

		return AppRestResponse.getListData("menus", list);
	}

	/**
	 *  查询子菜单/权限
	 */
	@RequestMapping(value = "/children", method = RequestMethod.GET, produces = { "application/json" })
	public String getChildren(GetChildrenByParentCodeEntity getChildrenByParentCodeEntity) {
		List<BaseMenu> list = menuService.getChildren(getChildrenByParentCodeEntity);

		return AppRestResponse.getListData("menus", list);
	}

	public static class GetChildrenByParentCodeEntity {

		private String parentCode;

		public String getParentCode() {
			return parentCode;
		}

		public void setParentCode(String parentCode) {
			this.parentCode = parentCode;
		}

	}

	/**
	 * 添加菜单
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/x-www-form-urlencoded:" }, produces = { "application/json" })
	public String post(PostEntity postEntity) {

		menuService.post(postEntity);

		return AppRestResponse.getNoData();
	}

	public static class PostEntity implements EntityValidator {
		@NotEmpty(message = "菜单名称不能为空")
		private String menuName;
		private String parentCode;
		@NotNull(message = "菜单类型(menuType) 1=菜单,2=权限")
		@Min(value = 1, message = "菜单类型(menuType) 1=菜单,2=权限")
		@Max(value = 2, message = "菜单类型(menuType) 1=菜单,2=权限")
		private Integer menuType;
		private String icon;
		private String routerState;
		private String permission;

		public String getMenuName() {
			return menuName;
		}

		public void setMenuName(String menuName) {
			this.menuName = menuName;
		}

		public String getParentCode() {
			return parentCode;
		}

		public void setParentCode(String parentCode) {
			this.parentCode = parentCode;
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

	}

	/**
	 * 修改菜单
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/x-www-form-urlencoded:" }, produces = { "application/json" })
	public String putById(PutByIdEntity putByIdEntity) {

		menuService.putById(putByIdEntity);

		return AppRestResponse.getNoData();
	}

	public static class PutByIdEntity {
		private String id;
		private String menuName;
		private String icon;
		private String routerState;
		private String permission;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getMenuName() {
			return menuName;
		}

		public void setMenuName(String menuName) {
			this.menuName = menuName;
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

	}

	/**
	 * 修改父菜单
	 */
	@RequestMapping(value = "/{id}/parent", method = RequestMethod.PUT, consumes = { "application/x-www-form-urlencoded:" }, produces = { "application/json" })
	public String putByIdParent(PutByIdParentEntity putByIdParentEntity) {

		menuService.putByIdParent(putByIdParentEntity);

		return AppRestResponse.getNoData();
	}

	public static class PutByIdParentEntity {

		private String id;
		private String parentCode;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getParentCode() {
			return parentCode;
		}

		public void setParentCode(String parentCode) {
			this.parentCode = parentCode;
		}

	}

	/**
	 * 修改排序
	 */
	@RequestMapping(value = "/sort", method = RequestMethod.PUT, consumes = { "application/x-www-form-urlencoded:" }, produces = { "application/json" })
	public String putSort(PutSortEntity putSortEntity) {

		menuService.putSort(putSortEntity);

		return AppRestResponse.getNoData();
	}

	public static class PutSortEntity {

		private String srcId;
		private String destId;

		public String getSrcId() {
			return srcId;
		}

		public void setSrcId(String srcId) {
			this.srcId = srcId;
		}

		public String getDestId() {
			return destId;
		}

		public void setDestId(String destId) {
			this.destId = destId;
		}

	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	public String delete(DeleteEntity deleteEntity) {

		menuService.delete(deleteEntity);

		return AppRestResponse.getNoData();
	}

	public static class DeleteEntity {
		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

	}

}
