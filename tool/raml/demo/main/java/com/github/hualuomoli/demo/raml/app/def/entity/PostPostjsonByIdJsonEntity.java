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
  private String username = "hualuomoli";
  private Address address;
  private String nickname;
  private java.util.List<Order> orders;
  
  public String getId(){
    return id;
  }
  
  public void setId(String id){
    this.id = id;
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
  
  public static class Address {
    private String name;
    private String couty;
    private String province = "37";
    private String city;
    
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
    
    
  }
  public static class Order {
    private String id = "1234567890";
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date = DateUtils.parse("2015-06-02");
    private java.util.List<Product> products;
    
    public String getId(){
      return id;
    }
    
    public void setId(String id){
      this.id = id;
    }
    public Date getDate(){
      return date;
    }
    
    public void setDate(Date date){
      this.date = date;
    }
    public java.util.List<Product> getProducts(){
      return products;
    }
    
    public void setProducts(java.util.List<Product> products){
      this.products = products;
    }
    
    public static class Product {
      private String id;
      private String name = "IPAD";
      
      public String getId(){
        return id;
      }
      
      public void setId(String id){
        this.id = id;
      }
      public String getName(){
        return name;
      }
      
      public void setName(String name){
        this.name = name;
      }


    }
    
  }
}
