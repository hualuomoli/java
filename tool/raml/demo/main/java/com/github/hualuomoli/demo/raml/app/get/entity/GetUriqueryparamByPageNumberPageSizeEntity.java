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

public class GetUriqueryparamByPageNumberPageSizeEntity implements EntityValidator {
  
  private Integer pageSize;
  private Integer pageNumber;
  @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
  private Date startDate;
  
  public Integer getPageSize(){
    return pageSize;
  }
  
  public void setPageSize(Integer pageSize){
    this.pageSize = pageSize;
  }
  public Integer getPageNumber(){
    return pageNumber;
  }
  
  public void setPageNumber(Integer pageNumber){
    this.pageNumber = pageNumber;
  }
  public Date getStartDate(){
    return startDate;
  }
  
  public void setStartDate(Date startDate){
    this.startDate = startDate;
  }
  
  
}
