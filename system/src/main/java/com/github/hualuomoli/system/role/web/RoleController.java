package com.github.hualuomoli.system.role.web;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.hualuomoli.mvc.annotation.RequestVersion;
import com.github.hualuomoli.mvc.rest.AppRestResponse;
import com.github.hualuomoli.system.base.entity.BaseMenu;
import com.github.hualuomoli.system.base.entity.BaseRole;
import com.github.hualuomoli.system.role.service.RoleService;

@RequestVersion
@RequestMapping(value = "/system/role")
@RestController(value = "com.github.hualuomoli.system.role.RoleController")
public class RoleController {

	@Autowired
	private RoleService roleService;

	/**
	 * 查询角色
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json" })
	public String get() {

		List<BaseRole> list = roleService.get();

		return AppRestResponse.getListData("roles", list);
	}

	/**
	 * 添加角色
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/x-www-form-urlencoded" }, produces = { "application/json" })
	public String post(PostEntity postEntity) {

		roleService.post(postEntity);

		return AppRestResponse.getNoData();
	}

	public static class PostEntity {

		private String roleCode;
		private String roleName;

		public String getRoleCode() {
			return roleCode;
		}

		public void setRoleCode(String roleCode) {
			this.roleCode = roleCode;
		}

		public String getRoleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

	}

	/**
	 * 修改角色
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/x-www-form-urlencoded" }, produces = { "application/json" })
	public String put(PutEntity putEntity) {

		roleService.put(putEntity);

		return AppRestResponse.getNoData();
	}

	public static class PutEntity {
		@NotEmpty(message = "角色ID (id) 不能为空")
		private String id;
		private String roleName;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getRoleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}
	}

	/**
	 * 修改角色
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	public String delete(DeleteEntity deleteEntity) {

		roleService.delete(deleteEntity);

		return AppRestResponse.getNoData();
	}

	public static class DeleteEntity {
		@NotEmpty(message = "角色ID (id) 不能为空")
		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}

	/**
	 * 获取角色的菜单
	 */
	@RequestMapping(value = "/{id}/menus", method = RequestMethod.GET, produces = { "application/json" })
	public String getMenus(GetMenusEntity getMenusEntity) {

		List<BaseMenu> list = roleService.getMenus(getMenusEntity);

		return AppRestResponse.getListData("menus", list);

	}

	public static class GetMenusEntity {
		@NotEmpty(message = "角色ID (id) 不能为空")
		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}

	/**
	 * 设置角色的菜单
	 */
	@RequestMapping(value = "/{id}/menus", method = RequestMethod.PUT, consumes = { "application/x-www-form-urlencoded" }, produces = { "application/json" })
	public String putMenus(PutMenusEntity putMenusEntity) {

		roleService.putMenus(putMenusEntity);

		return AppRestResponse.getNoData();

	}

	public static class PutMenusEntity {
		@NotEmpty(message = "角色ID (id) 不能为空")
		private String id;
		private String menus;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getMenus() {
			return menus;
		}

		public void setMenus(String menus) {
			this.menus = menus;
		}
	}

}
