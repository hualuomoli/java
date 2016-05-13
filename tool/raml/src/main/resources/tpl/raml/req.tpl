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

import com.github.hualuomoli.commons.util.DateUtils;
import com.github.hualuomoli.mvc.validator.EntityValidator;

<#-- 请求实体类 -->
public class ${className} implements EntityValidator {
  
  <#list params as param>
  <#list param.annos as anno>
  ${anno}
  </#list>
  private ${param.type} ${param.name}<#if param.def??> = ${param.def}</#if>;
  </#list>
  <#list jsonParams as jsonParam>
  <#list jsonParam.annos as anno>
  ${anno}
  </#list>
  private ${jsonParam.type} ${jsonParam.name}<#if jsonParam.def??> = ${jsonParam.def}</#if>;
  </#list>
  
  <#list params as param>
  public ${param.type} get${param.name?cap_first}(){
    return ${param.name};
  }
  
  public void set${param.name?cap_first}(${param.type} ${param.name}){
    this.${param.name} = ${param.name};
  }
  </#list>
  
  <#list jsonParams as jsonParam>
  public ${jsonParam.type} get${jsonParam.name?cap_first}(){
    return ${jsonParam.name};
  }
  
  public void set${jsonParam.name?cap_first}(${jsonParam.type} ${jsonParam.name}){
    this.${jsonParam.name} = ${jsonParam.name};
  }
  </#list>
  
  <#-- 二级 -->
  <#list jsonParams as jsonParam>
  <#-- 有子节点才处理 -->
  <#if jsonParam.children?size gt 0>
  public static class ${jsonParam.className} {
    <#list jsonParam.children as firstChild>
    <#list firstChild.annos as anno>
    ${anno}
    </#list>
    private ${firstChild.type} ${firstChild.name}<#if firstChild.def??> = ${firstChild.def}</#if>;
    </#list>
    
    <#list jsonParam.children as firstChild>
    public ${firstChild.type} get${firstChild.name?cap_first}(){
      return ${firstChild.name};
    }
    
    public void set${firstChild.name?cap_first}(${firstChild.type} ${firstChild.name}){
      this.${firstChild.name} = ${firstChild.name};
    }
    </#list>
    
    <#-- 三级 -->
    <#list jsonParam.children as firstChild>
    <#-- 有子节点才处理 -->
    <#if firstChild.children?size gt 0>
    public static class ${firstChild.className} {
      <#list firstChild.children as secondChild>
      <#list secondChild.annos as anno>
      ${anno}
      </#list>
      private ${secondChild.type} ${secondChild.name}<#if secondChild.def??> = ${secondChild.def}</#if>;
      </#list>
      
      <#list firstChild.children as secondChild>
      public ${secondChild.type} get${secondChild.name?cap_first}(){
        return ${secondChild.name};
      }
      
      public void set${secondChild.name?cap_first}(${secondChild.type} ${secondChild.name}){
        this.${secondChild.name} = ${secondChild.name};
      }
      </#list>

      <#-- 四级 -->
      <#list firstChild.children as secondChild>
      <#-- 有子节点才处理 -->
      <#if secondChild.children?size gt 0>
      public static class ${secondChild.className} {
        <#list secondChild.children as thirdChild>
        <#list thirdChild.annos as anno>
        ${anno}
        </#list>
        private ${thirdChild.type} ${thirdChild.name}<#if thirdChild.def??> = ${thirdChild.def}</#if>;
        </#list>
        
        <#list secondChild.children as thirdChild>
        public ${thirdChild.type} get${thirdChild.name?cap_first}(){
          return ${thirdChild.name};
        }
        
        public void set${thirdChild.name?cap_first}(${thirdChild.type} ${thirdChild.name}){
          this.${thirdChild.name} = ${thirdChild.name};
        }
        </#list>
        
        <#-- 五级 -->
        <#list secondChild.children as thirdChild>
        <#-- 有子节点才处理 -->
        <#if thirdChild.children?size gt 0>
        public static class ${thirdChild.className} {
          <#list thirdChild.children as fourthChild>
          <#list fourthChild.annos as anno>
          ${anno}
          </#list>
          private ${fourthChild.type} ${fourthChild.name}<#if fourthChild.def??> = ${fourthChild.def}</#if>;
          </#list>
          
          <#list thirdChild.children as fourthChild>
          public ${fourthChild.type} get${fourthChild.name?cap_first}(){
            return ${fourthChild.name};
          }
          
          public void set${fourthChild.name?cap_first}(${fourthChild.type} ${fourthChild.name}){
            this.${fourthChild.name} = ${fourthChild.name};
          }
          </#list>
        }
        </#if>
        </#list>
        <#-- ./五级 -->

      }
      </#if>
      </#list>
      <#-- ./四级 -->

    }
    </#if>
    </#list>
    <#-- ./三级 -->
    
  }
  </#if>
  </#list>
  <#-- ./二级 -->
}
<#-- ./请求实体类 -->