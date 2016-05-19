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

public class PostJsonResultJsonEntityOrder {
  
  private String id;
  private Date date;
  private java.util.List<PostJsonResultJsonEntityProduct> products;
  
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
  public java.util.List<PostJsonResultJsonEntityProduct> getProducts(){
    return products;
  }
  
  public void setProducts(java.util.List<PostJsonResultJsonEntityProduct> products){
    this.products = products;
  }
  
}
