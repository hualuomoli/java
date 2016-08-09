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

public class PostJsonResultJsonEntityOrder {
  
  @JSONField(format = "yyyy-MM-dd")
  private Date date;
  private String id;
  private java.util.List<PostJsonResultJsonEntityProduct> products;
  
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
  public java.util.List<PostJsonResultJsonEntityProduct> getProducts(){
    return products;
  }
  
  public void setProducts(java.util.List<PostJsonResultJsonEntityProduct> products){
    this.products = products;
  }
  
}
