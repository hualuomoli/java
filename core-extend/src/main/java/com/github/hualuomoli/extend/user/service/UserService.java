package com.github.hualuomoli.extend.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.commons.util.YamlUtils;
import com.github.hualuomoli.extend.base.entity.BaseMenu;
import com.github.hualuomoli.extend.base.entity.BaseRoleMenu;
import com.github.hualuomoli.extend.base.entity.BaseUser;
import com.github.hualuomoli.extend.base.entity.BaseUserRole;
import com.github.hualuomoli.extend.base.service.BaseMenuService;
import com.github.hualuomoli.extend.base.service.BaseRoleMenuService;
import com.github.hualuomoli.extend.base.service.BaseUserRoleService;
import com.github.hualuomoli.extend.base.service.BaseUserService;
import com.github.hualuomoli.extend.constant.DataType;
import com.github.hualuomoli.plugin.secure.Digest;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Service(value = "com.github.hualuomoli.extend.user.service.UserService")
@Transactional(readOnly = true)
public class UserService {

	@Autowired
	private BaseUserRoleService baseUserRoleService;
	@Autowired
	private BaseRoleMenuService baseRoleMenuService;
	@Autowired
	private BaseMenuService baseMenuService;
	@Autowired
	private BaseUserService baseUserService;
	private PasswordDigest passwordDigest = new PasswordDigest();

	// 查询用户具有的角色
	public HashSet<String> getUserRoles(String username) {

		HashSet<String> roles = Sets.newHashSet();

		// query
		BaseUserRole baseUserRole = new BaseUserRole();
		baseUserRole.setUsername(username);

		List<BaseUserRole> list = baseUserRoleService.findList(baseUserRole);

		for (BaseUserRole bur : list) {
			roles.add(bur.getRoleCode());
		}

		return roles;
	}

	// 查询用户具有的权限
	public HashSet<String> getUserPermission(String username) {

		HashSet<String> permission = Sets.newHashSet();

		// 用户的角色
		BaseUserRole baseUserRole = new BaseUserRole();
		baseUserRole.setUsername(username);
		List<BaseUserRole> baseUserRoles = baseUserRoleService.findList(baseUserRole);
		// 没有角色
		if (baseUserRoles == null || baseUserRoles.size() == 0) {
			return permission;
		}

		// 角色菜单
		Set<String> roleCodes = Sets.newHashSet();
		for (BaseUserRole bur : baseUserRoles) {
			roleCodes.add(bur.getRoleCode());
		}
		BaseRoleMenu baseRoleMenu = new BaseRoleMenu();
		baseRoleMenu.setRoleCodeArray(roleCodes.toArray(new String[] {}));
		List<BaseRoleMenu> baseRoleMenus = baseRoleMenuService.findList(baseRoleMenu);
		if (baseRoleMenus == null || baseRoleMenus.size() == 0) {
			return permission;
		}

		// 菜单
		Set<String> menuCodes = Sets.newHashSet();
		for (BaseRoleMenu brm : baseRoleMenus) {
			menuCodes.add(brm.getMenuCode());
		}

		// 查询菜单
		BaseMenu baseMenu = new BaseMenu();
		baseMenu.setMenuCodeArray(menuCodes.toArray(new String[] {}));
		baseMenu.setMenuType(DataType.MENU_TYPE_PERMISSION.getValue());
		List<BaseMenu> baseMenus = baseMenuService.findList(baseMenu);

		// add
		for (BaseMenu bm : baseMenus) {
			permission.add(bm.getPermission());
		}

		return permission;
	}

	// 设置用户角色
	@Transactional(readOnly = false)
	public void setUserRoles(String username, String... roles) {
		BaseUserRole baseUserRole = new BaseUserRole();
		baseUserRole.setUsername(username);
		List<BaseUserRole> list = baseUserRoleService.findList(baseUserRole);
		// remove
		if (list != null && list.size() > 0) {
			List<String> ids = Lists.newArrayList();
			for (BaseUserRole bur : list) {
				ids.add(bur.getId());
			}
			baseUserRoleService.deleteByIds(ids);
		}
		// set
		list = Lists.newArrayList();
		for (String roleCode : roles) {
			BaseUserRole bur = new BaseUserRole();
			bur.setUsername(username);
			bur.setRoleCode(roleCode);
			list.add(bur);
		}
		baseUserRoleService.batchInsert(list);
	}

	// 根据用户名获取用户
	public BaseUser getByUsername(String username) {
		BaseUser baseUser = new BaseUser();
		baseUser.setUsername(username);
		return this.getBaseUser(baseUser);
	}

	// 根据手机号码获取用户
	public BaseUser getByPhone(String phone) {
		BaseUser baseUser = new BaseUser();
		baseUser.setPhone(phone);
		return this.getBaseUser(baseUser);
	}

	// 根据手机号码获取用户
	public BaseUser getByEmail(String email) {
		BaseUser baseUser = new BaseUser();
		baseUser.setEmail(email);
		return this.getBaseUser(baseUser);
	}

	// 获取用户
	private BaseUser getBaseUser(BaseUser baseUser) {
		List<BaseUser> list = baseUserService.findList(baseUser);
		if (list == null || list.size() == 0) {
			return null;
		}
		if (list.size() == 1) {
			return list.get(0);
		}
		throw new RuntimeException("need one but find " + list.size());
	}

	// 更新用户手机号码
	public void updatePhone(String id, String phone) {
		BaseUser baseUser = new BaseUser();
		baseUser.setId(id);
		baseUser.setPhone(phone);
		baseUserService.update(baseUser);
	}

	// 更新用户邮箱
	public void updateEmail(String id, String email) {
		BaseUser baseUser = new BaseUser();
		baseUser.setId(id);
		baseUser.setEmail(email);
		baseUserService.update(baseUser);
	}

	// 密码加密
	public String encryptPassword(String origin) {
		return passwordDigest.signString(origin);
	}

	// 验证密码
	public boolean checkPassword(String origin, String encryptPassword) {
		return passwordDigest.validString(origin, encryptPassword);
	}

	public static class PasswordDigest extends Digest {

		public PasswordDigest() {
			Security security = YamlUtils.getInstance().getObject("password", Security.class, "digest");

			this.setAlgorithm(security.algorithm);
			this.setSaltNumber(security.saltNumber);
			this.setIterations(security.iterations);
		}

		public static class Security {
			private String algorithm; // 算法
			private Integer saltNumber; // 盐的长度
			private Integer iterations; // 迭代次数

			public Security() {
			}

			public String getAlgorithm() {
				return algorithm;
			}

			public void setAlgorithm(String algorithm) {
				this.algorithm = algorithm;
			}

			public Integer getSaltNumber() {
				return saltNumber;
			}

			public void setSaltNumber(Integer saltNumber) {
				this.saltNumber = saltNumber;
			}

			public Integer getIterations() {
				return iterations;
			}

			public void setIterations(Integer iterations) {
				this.iterations = iterations;
			}

		}
	}

}
