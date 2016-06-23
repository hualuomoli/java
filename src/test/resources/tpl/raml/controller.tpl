package ${packageName};

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.hualuomoli.base.validator.EntityValidator;
import com.github.hualuomoli.commons.json.JsonMapper;

/**
 * @Description ${desc!''}
 * @Author ${author!''}
 * @Date ${date!''}
 * @Version ${version!''}
 */
@Controller(value = "${packageName}.${javaName}")
@RequestMapping(value = "${uri}")
public class ${javaName} {

	private static final Logger logger = LoggerFactory.getLogger(${javaName}.class);
	
	// @Autowired
    // private ${serviceJavaName} ${serviceJavaName?uncap_first};
	
	<#list methods as method>
	<#-- method -->
	/**
	<#list method.uriParams as uriParam>
	 * @param ${uriParam.name} ${uriParam.comment}
	</#list>
	<#list method.fileParams as fileParam>
	 * @param ${fileParam.name} ${fileParam.comment}
	</#list>
	 */
	public ${method.resultType} ${method.methodName}(
	<#list method.uriParams as uriParam>
	<#list uriParam.annos as anno>
	${anno}
	</#list>
	${uriParam.type} ${uriParam.name},
	</#list>
	<#list method.fileParams as fileParam>
	<#list fileParam.annos as anno>
	${anno}
	</#list>
	${fileParam.type} ${fileParam.name},
	</#list>
	${method.request.className} ${method.request.className?uncap_first},
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		<#list method.uriParams as uriParam>
		${method.request.className?uncap_first}.set${uriParam.name?cap_first}(${uriParam.name});
		</#list>
		<#list method.fileParams as fileParam>
		${method.request.className?uncap_first}.set${fileParam.name?cap_first}(${fileParam.name});
		</#list>
		// TODO
		return null;
	}
	<#-- ./method -->
	
	<#-- 请求实体类 -->
	public static class ${method.request.className} {
		
		<#list method.request.params as param>
		<#list param.annos as anno>
		${anno}
		</#list>
		private ${param.type} ${param.name};
		</#list>
		<#list method.request.jsonParams as jsonParam>
		<#list jsonParam.annos as anno>
		${anno}
		</#list>
		private ${jsonParam.type} ${jsonParam.name};
		</#list>
		
		<#list method.request.params as param>
		public ${param.type} get${param.name?cap_first}(){
			return ${param.name};
		}
		
		public void set${param.name?cap_first}(${param.type} ${param.name}){
			this.${param.name} = ${param.name};
		}
		</#list>
		
		<#list method.request.jsonParams as jsonParam>
		public ${jsonParam.type} get${jsonParam.name?cap_first}(){
			return ${jsonParam.name};
		}
		
		public void set${jsonParam.name?cap_first}(${jsonParam.type} ${jsonParam.name}){
			this.${jsonParam.name} = ${jsonParam.name};
		}
		</#list>
		
		<#-- 二级 -->
		<#list method.request.jsonParams as jsonParam>
		<#-- 有子节点才处理 -->
		<#if jsonParam.children?size gt 0>
		public static class ${jsonParam.className} {
			<#list jsonParam.children as firstChild>
			<#list firstChild.annos as anno>
			${anno}
			</#list>
			private ${firstChild.type} ${firstChild.name};
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
				private ${secondChild.type} ${secondChild.name};
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
					private ${thirdChild.type} ${thirdChild.name};
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
						private ${fourthChild.type} ${fourthChild.name};
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

	<#-- 响应实体类 -->
	<#if method.response??>
	public static class ${method.response.className} {
		
		<#list method.response.jsonParams as jsonParam>
		<#list jsonParam.annos as anno>
		${anno}
		</#list>
		private ${jsonParam.type} ${jsonParam.name};
		</#list>
		
		<#list method.response.jsonParams as jsonParam>
		public ${jsonParam.type} get${jsonParam.name?cap_first}(){
			return ${jsonParam.name};
		}
		
		public void set${jsonParam.name?cap_first}(${jsonParam.type} ${jsonParam.name}){
			this.${jsonParam.name} = ${jsonParam.name};
		}
		</#list>
		
		<#-- 二级 -->
		<#list method.response.jsonParams as jsonParam>
		<#-- 有子节点才处理 -->
		<#if jsonParam.children?size gt 0>
		public static class ${jsonParam.className} {
			<#list jsonParam.children as firstChild>
			<#list firstChild.annos as anno>
			${anno}
			</#list>
			private ${firstChild.type} ${firstChild.name};
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
				private ${secondChild.type} ${secondChild.name};
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
					private ${thirdChild.type} ${thirdChild.name};
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
						private ${fourthChild.type} ${fourthChild.name};
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
	</#if>
	<#-- ./响应实体类 -->
		
	</#list>
	<#-- ./methods -->

}
