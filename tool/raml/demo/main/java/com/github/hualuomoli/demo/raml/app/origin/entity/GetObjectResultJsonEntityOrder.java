package com.github.hualuomoli.demo.raml.app.origin.entity;

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

public class GetObjectResultJsonEntityOrder {
  
  private String id;
  private Double price;
  private String name;
  
  public String getId(){
    return id;
  }
  
  public void setId(String id){
    this.id = id;
  }
  public Double getPrice(){
    return price;
  }
  
  public void setPrice(Double price){
    this.price = price;
  }
  public String getName(){
    return name;
  }
  
  public void setName(String name){
    this.name = name;
  }
  
}
