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

public class PostUrlencodedUrlEncodedEntity implements EntityValidator {
  
  @NotNull(message = "年龄 - age 必填")
  @NotEmpty(message = "年龄 - age不能为空")
  private Integer[] age;
  @NotNull(message = "工资 - salary 必填")
  @NotEmpty(message = "工资 - salary不能为空")
  private Double[] salary;
  private String[] phone;
  
  public Integer[] getAge(){
    return age;
  }
  
  public void setAge(Integer[] age){
    this.age = age;
  }
  public Double[] getSalary(){
    return salary;
  }
  
  public void setSalary(Double[] salary){
    this.salary = salary;
  }
  public String[] getPhone(){
    return phone;
  }
  
  public void setPhone(String[] phone){
    this.phone = phone;
  }
  
  
}
