package com.github.hualuomoli.demo.raml.app.multipart.entity;

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

public class PostFileByIdFileEntity implements EntityValidator {
  
  private String id;
  @NotNull(message = "头像 - photo 必填")
  private MultipartFile photo;
  @NotNull(message = "用户名 - username 必填")
  @NotBlank(message = "用户名 - username不能为空")
  private String username;
  
  public String getId(){
    return id;
  }
  
  public void setId(String id){
    this.id = id;
  }
  public MultipartFile getPhoto(){
    return photo;
  }
  
  public void setPhoto(MultipartFile photo){
    this.photo = photo;
  }
  public String getUsername(){
    return username;
  }
  
  public void setUsername(String username){
    this.username = username;
  }
  
  
}
