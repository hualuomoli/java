package com.github.hualuomoli.demo.version;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.hualuomoli.commons.util.YamlUtils;
import com.github.hualuomoli.test.AbstractContextControllerTest;

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

		mockMvc.perform(this.get("") //
				.header(versionName, "")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent("0"))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test02V1() throws Exception {

		mockMvc.perform(this.get("") //
				.header(versionName, "v1.0.0")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent("v1.0.0"))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test03V3_2_1() throws Exception {

		mockMvc.perform(this.get("") //
				.header(versionName, "v3.2.1")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent("v3.2.1"))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test04Empty() throws Exception {

		mockMvc.perform(this.get("")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent("0"))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test05LoertDefinedVersion() throws Exception {

		mockMvc.perform(this.get("") //
				.header(versionName, "v0.9.8")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent("0"))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test06BetweenDefinedVersion() throws Exception {

		mockMvc.perform(this.get("") //
				.header(versionName, "v2.0.0")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent("v1.0.0"))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test07HigherDefinedVersion() throws Exception {

		mockMvc.perform(this.get("") //
				.header(versionName, "v10.1.2")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent("v3.2.1"))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

}
