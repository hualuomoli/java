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

public class PostJsonResultJsonEntityProduct {
  
  private String name;
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
