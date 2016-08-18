package com.github.hualuomoli.demo.raml.app.ret.entity;

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

public class GetPagedataResultJsonEntityUser {
  
  private String sex;
  private String username;
  private String nickname;
  private Integer age;
  
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
  public String getNickname(){
    return nickname;
  }
  
  public void setNickname(String nickname){
    this.nickname = nickname;
  }
  public Integer getAge(){
    return age;
  }
  
  public void setAge(Integer age){
    this.age = age;
  }
  
}
