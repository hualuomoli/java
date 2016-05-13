package com.github.hualuomoli.demo.raml.app.get.entity;

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

public class GetQueryparamEntity implements EntityValidator {
  
  @NotNull(message = "用户名 - username 必填")
  @NotBlank(message = "用户名 - username不能为空")
  private String username;
  @NotNull(message = "密码 - password 必填")
  @NotBlank(message = "密码 - password不能为空")
  private String password;
  
  public String getUsername(){
    return username;
  }
  
  public void setUsername(String username){
    this.username = username;
  }
  public String getPassword(){
    return password;
  }
  
  public void setPassword(String password){
    this.password = password;
  }
  
  
}
