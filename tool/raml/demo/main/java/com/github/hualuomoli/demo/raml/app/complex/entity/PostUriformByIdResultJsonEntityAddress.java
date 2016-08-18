package com.github.hualuomoli.demo.raml.app.complex.entity;

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

public class PostUriformByIdResultJsonEntityAddress {
  
  private String name;
  private String couty;
  private String province;
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
