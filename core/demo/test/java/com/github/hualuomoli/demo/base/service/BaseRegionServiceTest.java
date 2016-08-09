package com.github.hualuomoli.demo.base.service;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.commons.util.RandomUtils;
import com.github.hualuomoli.demo.base.entity.BaseRegion;
import com.github.hualuomoli.test.AbstractContextServiceTest;
import com.google.common.collect.Lists;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母排序
public class BaseRegionServiceTest extends AbstractContextServiceTest {

	@Autowired
	private BaseRegionService regionService;
	private static String id = "1";

	@Test
	public void test00Clear() {
		BaseRegion baseRegion = new BaseRegion();
		List<BaseRegion> list = regionService.findList(baseRegion);
		List<String> ids = Lists.newArrayList();
		for (BaseRegion region : list) {
			ids.add(region.getId());
		}
		regionService.deleteByIds(ids);
	}

	@Test
	public void test01Insert() {
		BaseRegion baseRegion = new BaseRegion();
		baseRegion.setId(id);
		baseRegion.setCode("37");
		baseRegion.setName("山东省");
		Integer count = regionService.insert(baseRegion);
		Assert.assertSame(1, count);
	}

	@Test
	public void test02GetBaseRegion() {
		BaseRegion baseRegion = new BaseRegion();
		baseRegion.setId(id);
		baseRegion = regionService.get(baseRegion);
		Assert.assertNotNull(baseRegion);
		Assert.assertEquals("山东省", baseRegion.getName());
	}

	@Test
	public void test03Update() {
		BaseRegion baseRegion = new BaseRegion();
		baseRegion.setId(id);
		baseRegion.setName("修改名称");
		baseRegion.setType(1);
		Integer count = regionService.update(baseRegion);
		Assert.assertSame(1, count);
	}

	@Test
	public void test04GetString() {
		BaseRegion baseRegion = regionService.get(id);
		Assert.assertNotNull(baseRegion);
		Assert.assertEquals("修改名称", baseRegion.getName());
		Assert.assertSame(1, baseRegion.getType());
	}

	@Test
	public void test05DeleteBaseRegion() {
		BaseRegion baseRegion = new BaseRegion();
		baseRegion.setId(id);
		Integer count = regionService.delete(baseRegion);
		Assert.assertSame(1, count);
		baseRegion = regionService.get(id);
		Assert.assertNull(baseRegion);
	}

	@Test
	public void test06Insert() {
		BaseRegion baseRegion = new BaseRegion();
		baseRegion.setId(id);
		baseRegion.setName("山东省");
		Integer count = regionService.insert(baseRegion);
		Assert.assertSame(1, count);
	}

	@Test
	public void test07DeleteString() {
		Integer count = regionService.delete(id);
		Assert.assertSame(1, count);
		BaseRegion baseRegion = regionService.get(id);
		Assert.assertNull(baseRegion);
	}

	@Test
	public void test08BatchInsert() {
		List<BaseRegion> list = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			BaseRegion baseRegion = new BaseRegion();
			baseRegion.setId(String.valueOf(i));
			baseRegion.setName(RandomUtils.getUUID());
			list.add(baseRegion);
		}
		Integer count = regionService.batchInsert(list);
		Assert.assertSame(20, count);

		list = regionService.findList(new BaseRegion());
		Assert.assertSame(20, list.size());
	}

	@Test
	public void test09DeleteByIdsStringArray() {
		String[] ids = new String[20];
		for (int i = 0; i < 20; i++) {
			ids[i] = String.valueOf(i);
		}
		Integer count = regionService.deleteByIds(ids);
		Assert.assertSame(20, count);

		List<BaseRegion> list = regionService.findList(new BaseRegion());
		Assert.assertSame(0, list.size());
	}

	@Test
	public void test10BatchInsert() {
		List<BaseRegion> list = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			BaseRegion baseRegion = new BaseRegion();
			baseRegion.setId(String.valueOf(i));
			baseRegion.setName(RandomUtils.getUUID());
			list.add(baseRegion);
		}
		Integer count = regionService.batchInsert(list);
		Assert.assertSame(20, count);
	}

	@Test
	public void test11DeleteByIdsCollectionOfString() {
		List<String> ids = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			ids.add(String.valueOf(i));
		}
		Integer count = regionService.deleteByIds(ids);
		Assert.assertSame(20, count);

		List<BaseRegion> list = regionService.findList(new BaseRegion());
		Assert.assertSame(0, list.size());
	}

}