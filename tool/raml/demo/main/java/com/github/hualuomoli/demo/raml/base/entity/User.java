package com.github.hualuomoli.demo.raml.base.entity;

import java.util.Date;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityIgnore;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.annotation.entity.EntityColumnType;

/**
 * @Description 默认值
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@EntityTable
public class User {

	@EntityColumn(comment = "年龄")
	private Integer age;
	@EntityColumn(comment = "昵称")
	private String nickname;
	@EntityColumn(comment = "性别")
	private String sex;
	@EntityColumn(comment = "用户名")
	private String username;

	private Address address;
	private Order orders;

	public Integer getAge(){
		return age;
	}

	public void setAge(Integer age){
		this.age = age;
	}
	public String getNickname(){
		return nickname;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	public String getSex(){
		return sex;
	}

	public void setSex(String sex){
		this.sex = sex;
	}
	public String getUsername(){
		return username;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public Address getAddress(){
		return address;
	}

	public void setAddress(Address address){
		this.address = address;
	}
	public Order getOrders(){
		return orders;
	}

	public void setOrders(Order orders){
		this.orders = orders;
	}


}
