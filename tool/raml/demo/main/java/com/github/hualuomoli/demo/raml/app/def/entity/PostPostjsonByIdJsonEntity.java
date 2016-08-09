package com.github.hualuomoli.demo.raml.app.def.entity;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.github.hualuomoli.commons.util.DateUtils;
import com.github.hualuomoli.mvc.validator.EntityValidator;

public class PostPostjsonByIdJsonEntity implements EntityValidator {
  
  private String id = "1234";
  private Address address;
  @NotNull(message = "昵称 - nickname 必填")
  @NotBlank(message = "昵称 - nickname不能为空")
  private String nickname;
  private java.util.List<Order> orders;
  @NotNull(message = "用户名 - username 必填")
  @NotBlank(message = "用户名 - username不能为空")
  private String username = "hualuomoli";
  
  public String getId(){
    return id;
  }
  
  public void setId(String id){
    this.id = id;
  }
  
  public Address getAddress(){
    return address;
  }
  
  public void setAddress(Address address){
    this.address = address;
  }
  public String getNickname(){
    return nickname;
  }
  
  public void setNickname(String nickname){
    this.nickname = nickname;
  }
  public java.util.List<Order> getOrders(){
    return orders;
  }
  
  public void setOrders(java.util.List<Order> orders){
    this.orders = orders;
  }
  public String getUsername(){
    return username;
  }
  
  public void setUsername(String username){
    this.username = username;
  }
  
  public static class Address {
    @NotNull(message = "省份 - province 必填")
    @NotBlank(message = "省份 - province不能为空")
    private String province = "37";
    @NotNull(message = "城市 - city 必填")
    @NotBlank(message = "城市 - city不能为空")
    private String city;
    @NotNull(message = "具体地址 - name 必填")
    @NotBlank(message = "具体地址 - name不能为空")
    private String name;
    @NotNull(message = "区县 - couty 必填")
    @NotBlank(message = "区县 - couty不能为空")
    private String couty;
    
    public String getProvince(){
      return province;
    }
    
    public void setProvince(String province){
      this.province = province;
    }
    public String getCity(){
      return city;
    }
    
    public void setCity(String city){
      this.city = city;
    }
    public String getName(){
      return name;
    }
    
    public void setName(String name){
      this.name = name;
    }
    public String getCouty(){
      return couty;
    }
    
    public void setCouty(String couty){
      this.couty = couty;
    }
    
    
  }
  public static class Order {
    @NotNull(message = "订单日期 - date 必填")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date = DateUtils.parse("2015-06-02");
    @NotNull(message = "订单号 - id 必填")
    @NotBlank(message = "订单号 - id不能为空")
    private String id = "1234567890";
    private java.util.List<Product> products;
    
    public Date getDate(){
      return date;
    }
    
    public void setDate(Date date){
      this.date = date;
    }
    public String getId(){
      return id;
    }
    
    public void setId(String id){
      this.id = id;
    }
    public java.util.List<Product> getProducts(){
      return products;
    }
    
    public void setProducts(java.util.List<Product> products){
      this.products = products;
    }
    
    public static class Product {
      @NotNull(message = "商品名 - name 必填")
      @NotBlank(message = "商品名 - name不能为空")
      private String name = "IPAD";
      @NotNull(message = "商品号 - id 必填")
      @NotBlank(message = "商品号 - id不能为空")
      private String id;
      
      public String getName(){
        return name;
      }
      
      public void setName(String name){
        this.name = name;
      }
      public String getId(){
        return id;
      }
      
      public void setId(String id){
        this.id = id;
      }


    }
    
  }
}
