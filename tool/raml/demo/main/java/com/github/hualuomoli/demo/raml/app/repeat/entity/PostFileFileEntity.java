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

import com.github.hualuomoli.commons.util.DateUtils;
import com.github.hualuomoli.mvc.validator.EntityValidator;

public class PostFileFileEntity implements EntityValidator {
  
  @Length(min = 1, max = 20, message = "用户名 - username长度在1-20之间")
  private String username;
  @NotNull(message = "头像 - photo 必填")
  @NotEmpty(message = "头像 - photo不能为空")
  private MultipartFile[] photo;
  
  public String getUsername(){
    return username;
  }
  
  public void setUsername(String username){
    this.username = username;
  }
  public MultipartFile[] getPhoto(){
    return photo;
  }
  
  public void setPhoto(MultipartFile[] photo){
    this.photo = photo;
  }
  
  
}
