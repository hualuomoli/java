package com.github.hualuomoli.demo.version;

import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.commons.util.YamlUtils;
import com.github.hualuomoli.test.AbstractContextControllerTest;
import com.google.common.collect.Maps;

// 需要一个个测试
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母排序
public class VersionControllerTest extends AbstractContextControllerTest {

	private static final String versionName = YamlUtils.getInstance().getString("restful", "version", "name");

	@Override
	protected String getControllerRequestUrl() {
		return "/demo/version";
	}

	@Test
	public void test01Init() throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", 0);
		map.put("msg", "this is default version");
		String content = JsonUtils.toJson(map);
		logger.debug("content = {}", content);

		mockMvc.perform(this.get("") //
				.header(versionName, "")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent(content))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test02V1() throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", 1);
		map.put("msg", "this is v1.0.0");
		String content = JsonUtils.toJson(map);
		logger.debug("content = {}", content);

		mockMvc.perform(this.get("") //
				.header(versionName, "v1.0.0")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent(content))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test03V3_2_1() throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", 3);
		map.put("msg", "this is v3.2.1");
		String content = JsonUtils.toJson(map);
		logger.debug("content = {}", content);

		mockMvc.perform(this.get("") //
				.header(versionName, "v3.2.1")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent(content))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test04Empty() throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", 0);
		map.put("msg", "this is default version");
		String content = JsonUtils.toJson(map);
		logger.debug("content = {}", content);

		mockMvc.perform(this.get("")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent(content))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test05LoertDefinedVersion() throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", 0);
		map.put("msg", "this is default version");
		String content = JsonUtils.toJson(map);
		logger.debug("content = {}", content);

		mockMvc.perform(this.get("") //
				.header(versionName, "v0.9.8")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent(content))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test06BetweenDefinedVersion() throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", 1);
		map.put("msg", "this is v1.0.0");
		String content = JsonUtils.toJson(map);
		logger.debug("content = {}", content);

		mockMvc.perform(this.get("") //
				.header(versionName, "v2.0.0")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent(content))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test07HigherDefinedVersion() throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", 3);
		map.put("msg", "this is v3.2.1");
		String content = JsonUtils.toJson(map);
		logger.debug("content = {}", content);

		mockMvc.perform(this.get("") //
				.header(versionName, "v10.1.2")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent(content))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

}
