package ${packageName}.entity;

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

<#-- 请求实体类 -->
public class ${className} {
  
  <#list jsonParams as jsonParam>
  private ${jsonParam.type} ${jsonParam.name};
  </#list>
  
  <#list jsonParams as jsonParam>
  public ${jsonParam.type} get${jsonParam.name?cap_first}(){
    return ${jsonParam.name};
  }
  
  public void set${jsonParam.name?cap_first}(${jsonParam.type} ${jsonParam.name}){
    this.${jsonParam.name} = ${jsonParam.name};
  }
  </#list>
  
}
<#-- ./请求实体类 -->