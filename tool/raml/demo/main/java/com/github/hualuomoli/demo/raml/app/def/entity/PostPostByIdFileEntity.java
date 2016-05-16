package com.github.hualuomoli.demo.raml.app.def.entity;

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

public class PostPostByIdFileEntity implements EntityValidator {
  
  private String id = "1234";
  @NotNull(message = "年龄 - age 必填")
  @Min(value = 10L, message = "年龄 - age最小值10")
  @Max(value = 100L, message = "年龄 - age最大值100")
  private Integer age = 20;
  @NotNull(message = "工资 - salary 必填")
  @Min(value = 100L, message = "工资 - salary最小值100")
  @Max(value = 99999L, message = "工资 - salary最大值99999")
  private Double salary = 100.2D;
  @NotBlank(message = "用户名 - phone不能为空")
  @Length(min = 1, max = 20, message = "用户名 - phone长度在1-20之间")
  @Pattern(regexp = "(\\d{3,4}-)?\\d{7,8}", message = "用户名 - phone格式不正确,如:0532-66668888")
  private String phone = "0532-66668888";
  @NotNull(message = "生日 - birthDay 必填")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date birthDay = DateUtils.parse("2016-07-13");
  @NotNull(message = "头像 - photo 必填")
  private MultipartFile photo;
  
  public String getId(){
    return id;
  }
  
  public void setId(String id){
    this.id = id;
  }
  public Integer getAge(){
    return age;
  }
  
  public void setAge(Integer age){
    this.age = age;
  }
  public Double getSalary(){
    return salary;
  }
  
  public void setSalary(Double salary){
    this.salary = salary;
  }
  public String getPhone(){
    return phone;
  }
  
  public void setPhone(String phone){
    this.phone = phone;
  }
  public Date getBirthDay(){
    return birthDay;
  }
  
  public void setBirthDay(Date birthDay){
    this.birthDay = birthDay;
  }
  public MultipartFile getPhoto(){
    return photo;
  }
  
  public void setPhoto(MultipartFile photo){
    this.photo = photo;
  }
  
  
}
