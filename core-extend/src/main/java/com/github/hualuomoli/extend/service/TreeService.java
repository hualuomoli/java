package com.github.hualuomoli.extend.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.extend.Tree;

@Service(value = "com.github.hualuomoli.extend.service.TreeService")
@Transactional(readOnly = true)
public class TreeService {

	private static final Logger logger = LoggerFactory.getLogger(TreeService.class);
	@Autowired
	private NoticeService noticeService;

	/**
	 * 修改排序
	 */
	public <T extends Tree> void updateSort(Class<T> cls, String srcId, String destId, TreeDealer<T> dealer) {
		T src = dealer.get(srcId);
		T dest = dealer.get(destId);

		if (src == null || dest == null) {
			return;
		}

		int srcDataSort = src.getDataSort();
		int destDataSort = dest.getDataSort();

		src = this.getInstance(cls);
		src.setId(srcId);
		src.setDataSort(destDataSort);

		dest = this.getInstance(cls);
		dest.setId(destId);
		dest.setDataSort(srcDataSort);

		dealer.update(src);
		dealer.update(dest);
	}

	/**
	 * 添加Tree信息
	 */
	@SuppressWarnings("unchecked")
	public <T extends Tree> void addTreeMessage(T t, String destParentCode, TreeDealer<T> dealer) {
		if (StringUtils.isBlank(destParentCode)) {
			destParentCode = Tree.ROOT_CODE;
		}
		Class<T> cls = (Class<T>) t.getClass();

		T destTemp = dealer.getUnique(destParentCode);

		String destParentFullName = destTemp.getFullName();

		Config config = this.getNext(cls, destParentCode, dealer);
		t.setDataCode(config.code);
		t.setDataSort(config.sort);
		t.SetDataLevel(config.level);
		if (StringUtils.equals(destParentCode, Tree.ROOT_CODE)) {
			t.setFullName(t.getDataName());
		} else {
			t.setFullName(destParentFullName + t.getNameSeparator() + t.getDataName());
		}
	}

	/**
	 * 修改父节点
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public <T extends Tree> void updateParent(T t, String destParentCode, TreeDealer<T> dealer) {

		if (StringUtils.isBlank(destParentCode)) {
			destParentCode = Tree.ROOT_CODE;
		}

		Class<T> cls = (Class<T>) t.getClass();

		// 获取元节点的父编码
		T srcTemp = dealer.get(t.getId());
		if (srcTemp == null) {
			return;
		}
		T destTemp = dealer.getUnique(destParentCode);
		if (destTemp == null) {
			return;
		}

		// 没移动
		if (StringUtils.equals(srcTemp.getParentCode(), destParentCode)) {
			return;
		}

		String srcParentFullName;
		String destParentFullName;

		// 原父编码
		String srcParentCode = srcTemp.getParentCode();

		if (StringUtils.equals(srcParentCode, Tree.ROOT_CODE)) {
			// 原节点为根节点
			srcParentFullName = "";
			String destFullName = destTemp.getFullName();
			String destDataName = destTemp.getDataName();
			destParentFullName = destFullName.substring(0, destFullName.length() - destDataName.length() - t.getNameSeparator().length());
		} else if (StringUtils.equals(destParentCode, Tree.ROOT_CODE)) {
			// 目标节点为根节点
			destParentFullName = "";
			String srcFullName = srcTemp.getFullName();
			String srcDataName = srcTemp.getDataName();
			srcParentFullName = srcFullName.substring(0, srcFullName.length() - srcDataName.length() - t.getNameSeparator().length());
		} else {

			// src
			String srcFullName = srcTemp.getFullName();
			String srcDataName = srcTemp.getDataName();
			srcParentFullName = srcFullName.substring(0, srcFullName.length() - srcDataName.length() - t.getNameSeparator().length());

			// dest
			String destFullName = destTemp.getFullName();
			String destDataName = destTemp.getDataName();
			destParentFullName = destFullName.substring(0, destFullName.length() - destDataName.length() - t.getNameSeparator().length());

		}

		Config config = this.getNext(cls, destParentCode, dealer);

		this.doUpdate(cls, t.getId(), config.code, destParentCode, config.sort, config.level, destParentFullName + t.getNameSeparator() + srcTemp.getDataName(),
				dealer);
		// 更新子集
		if (StringUtils.equals(srcParentCode, Tree.ROOT_CODE)) {
			srcParentCode = "";
		}
		if (StringUtils.equals(destParentCode, Tree.ROOT_CODE)) {
			destParentCode = "";
		}
		this.updateChildre(cls, t.getNameSeparator(), srcParentFullName, destParentFullName, srcParentCode, destParentCode, dealer);
	}

	// 更新子集
	private <T extends Tree> void updateChildre(Class<T> cls, String nameSeparator, String srcParentFullName, String destParentFullName, String srcParentCode,
			String destParentCode, TreeDealer<T> dealer) {
		List<T> children = this.getChildren(cls, srcParentCode, dealer);
		if (children == null || children.size() == 0) {
			return;
		}
		for (T child : children) {
			String fullName = destParentFullName + nameSeparator + child.getDataName();
			String dataCode = destParentCode + child.getDataCode().substring(srcParentCode.length());
			this.doUpdate(cls, child.getId(), dataCode, destParentCode, child.getDataSort(), dataCode.length() / 2, fullName, dealer);
			// 子集
			this.updateChildre(cls, nameSeparator, srcParentFullName, destParentFullName, srcParentCode, destParentCode, dealer);
		}
	}

	// 执行更新
	private <T extends Tree> void doUpdate(Class<T> cls, String id, String dataCode, String parentCode, Integer dataSort, Integer dataLevel, String fullName,
			TreeDealer<T> dealer) {
		// 设置当前结点
		T data = this.getInstance(cls);
		data.setFullName(fullName);
		data.setParentCode(parentCode);
		data.setDataCode(dataCode);
		data.setDataSort(dataSort);
		data.SetDataLevel(dataLevel);

		// 修改、通知
		dealer.update(data);
		noticeService.notice(data);
	}

	// 处理者service
	public static interface TreeDealer<T extends Tree> {

		T get(String id);

		T getUnique(String code);

		int update(T t);

		List<T> findList(T t);

	}

	private <T extends Tree> T getInstance(Class<T> cls) {
		try {
			return (T) cls.newInstance();
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
			throw new RuntimeException(e);
		}
	}

	private <T extends Tree> List<T> getChildren(Class<T> cls, String parentCode, TreeDealer<T> dealer) {
		T temp = this.getInstance(cls);
		temp.setParentCode(parentCode);
		// 查询集合
		return dealer.findList(temp);
	}

	// 下一个菜单编码
	private <T extends Tree> Config getNext(Class<T> cls, String destParentCode, TreeDealer<T> dealer) {
		// 查询集合
		List<T> list = this.getChildren(cls, destParentCode, dealer);

		// 父编码前缀
		String pre = StringUtils.equals(destParentCode, Tree.ROOT_CODE) ? "" : destParentCode;

		Config config = new Config();

		if (list == null || list.size() == 0) {
			// 没有子集
			config.code = pre + "01";
			config.sort = 1;
			config.level = pre.length() / 2 + 1;
		} else {
			// sort
			Collections.sort(list, new Comparator<T>() {

				@Override
				public int compare(T o1, T o2) {
					return o2.getDataSort() - o1.getDataSort();
				}
			});
			//
			config.sort = list.get(0).getDataSort() + 1;
			config.level = list.get(0).getDataLevel();

			// code
			final int length = pre.length();
			Collections.sort(list, new Comparator<T>() {

				@Override
				public int compare(T o1, T o2) {
					String code1 = o1.getDataCode().substring(length);
					String code2 = o2.getDataCode().substring(length);
					return Integer.parseInt(code1) - Integer.parseInt(code2);
				}
			});
			int max = 0;

			// 判断数据内部是否有删除的数据
			for (int i = 0; i < list.size(); i++) {
				T tt = list.get(i);
				String code = tt.getDataCode().substring(length);
				if (max != Integer.parseInt(code)) {
					max = Integer.parseInt(code);
					break;
				}
			}
			if (max == 0) {
				String code = list.get(list.size() - 1).getDataCode().substring(length);
				max = Integer.parseInt(code) + 1;
			}

			if (max >= 9) {
				config.code = pre + String.valueOf(max);
			} else {
				config.code = pre + "0" + max;
			}
		}

		return config;
		// end
	}

	private class Config {
		private String code;
		private Integer sort;
		private Integer level;
	}

}
