package com.github.hualuomoli.demo.version;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.hualuomoli.test.AbstractContextControllerTest;

// 需要一个个测试
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母排序
public class VersionControllerTest extends AbstractContextControllerTest {

	@Override
	protected String getControllerRequestUrl() {
		return "/demo/version";
	}

	@Test
	public void test01Init() throws Exception {
		mockMvc.perform(this.get("") //
				.header("apt-version", "")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent("3.2.1"))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test02V1() throws Exception {
		mockMvc.perform(this.get("") //
				.header("apt-version", "v1.0.0")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent("1.0.0"))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void test03V3_2_1() throws Exception {
		mockMvc.perform(this.get("") //
				.header("apt-version", "v3.2.1")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andDo(this.checkContent("3.2.1"))//
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

}
