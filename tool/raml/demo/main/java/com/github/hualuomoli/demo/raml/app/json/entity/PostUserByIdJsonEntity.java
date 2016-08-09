package com.github.hualuomoli.demo.raml.app.json.entity;

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

public class PostUserByIdJsonEntity implements EntityValidator {
  
  private String id;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date birthDay = DateUtils.parse("2016-05-13");
  @NotNull(message = "性别 - sex 必填")
  @NotBlank(message = "性别 - sex不能为空")
  private String sex;
  @NotNull(message = "昵称 - nickname 必填")
  @NotBlank(message = "昵称 - nickname不能为空")
  private String nickname;
  private Integer age;
  @NotNull(message = "用户名 - username 必填")
  @NotBlank(message = "用户名 - username不能为空")
  private String username;
  
  public String getId(){
    return id;
  }
  
  public void setId(String id){
    this.id = id;
  }
  
  public Date getBirthDay(){
    return birthDay;
  }
  
  public void setBirthDay(Date birthDay){
    this.birthDay = birthDay;
  }
  public String getSex(){
    return sex;
  }
  
  public void setSex(String sex){
    this.sex = sex;
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
  public String getUsername(){
    return username;
  }
  
  public void setUsername(String username){
    this.username = username;
  }
  
}
