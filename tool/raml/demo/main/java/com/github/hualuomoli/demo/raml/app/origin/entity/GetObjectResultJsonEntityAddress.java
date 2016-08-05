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

public class GetObjectResultJsonEntityAddress {
  
  private String county;
  private String province;
  private String city;
  
  public String getCounty(){
    return county;
  }
  
  public void setCounty(String county){
    this.county = county;
  }
  public String getProvince(){
    return province;
  }
  
  public void setProvince(String province){
    this.province = province;
  }
  public String getCity(){
    return city;
  }
  
  public void setCity(String city){
    this.city = city;
  }
  
}
