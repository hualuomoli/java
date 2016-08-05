package com.github.hualuomoli.demo.raml.app.repeat.entity;

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

import com.alibaba.fastjson.annotation.JSONField;

public class PostJsonResultJsonEntityUser {
  
  private String username;
  private PostJsonResultJsonEntityAddress address;
  private String nickname;
  private java.util.List<PostJsonResultJsonEntityOrder> orders;
  
  public String getUsername(){
    return username;
  }
  
  public void setUsername(String username){
    this.username = username;
  }
  public PostJsonResultJsonEntityAddress getAddress(){
    return address;
  }
  
  public void setAddress(PostJsonResultJsonEntityAddress address){
    this.address = address;
  }
  public String getNickname(){
    return nickname;
  }
  
  public void setNickname(String nickname){
    this.nickname = nickname;
  }
  public java.util.List<PostJsonResultJsonEntityOrder> getOrders(){
    return orders;
  }
  
  public void setOrders(java.util.List<PostJsonResultJsonEntityOrder> orders){
    this.orders = orders;
  }
  
}
