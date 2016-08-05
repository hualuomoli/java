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

public class GetListResultJsonEntity {
  
  private String value;
  private String label;
  
  public String getValue(){
    return value;
  }
  
  public void setValue(String value){
    this.value = value;
  }
  public String getLabel(){
    return label;
  }
  
  public void setLabel(String label){
    this.label = label;
  }
  
}
