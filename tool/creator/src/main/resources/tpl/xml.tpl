<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${fullName}">

	<sql id="columns">
	<#list entity.attributes as attribute>
		<#if attribute.entity>
		<#-- 实体类 -->
		${attribute.dbName}_id${attribute.dbBlanks} as "${attribute.name}.id"<#if entity.attributes?size - attribute_index gt 1>,</#if>
		<#else>
		<#-- 普通类型 -->
		${attribute.dbName}${attribute.dbBlanks} as "${attribute.name}"<#if entity.attributes?size - attribute_index gt 1>,</#if>
		</#if>
	</#list>
	</sql>
	
	<sql id="querys">
	<#list entity.attributes as attribute>
		<#if attribute.entity>
		<#-- 实体类 -->
		<if test="${attribute.name} != null and ${attribute.name}.id != null and ${attribute.name}.id != ''">	
			${attribute.dbName}_id${attribute.dbBlanks} = ${r"#{"}${attribute.name}.id${r"}"}${attribute.blanks}
		</if>
		<#elseif attribute.string>
		<#-- 字符串 -->
		<if test="${attribute.name} != null and ${attribute.name} != ''">	
			${attribute.dbName}${attribute.dbBlanks} = ${r"#{"}${attribute.name}${r"}"}${attribute.blanks}
		</if>
		<#else>
		<#-- 非字符串普通类型 -->
		<if test="${attribute.name} != null">	
			${attribute.dbName}${attribute.dbBlanks} = ${r"#{"}${attribute.name}${r"}"}${attribute.blanks}
		</if>
		</#if>
	</#list>
	</sql>
	
	<#-- 根据主键查询 -->
	<select id="get" resultType="${entity.fullName}">
		select 
			<include refid="columns" />
		from ${entity.dbName}
		where id =  ${r"#{"}id${r"}"}
	</select>
	
	<insert id="insert">
		insert into ${entity.dbName} (
		<#list entity.attributes as attribute>
			<#if attribute.entity>
			<#-- 实体类 -->
			${attribute.dbName}_id${attribute.dbBlanks}<#if entity.attributes?size - attribute_index gt 1>,</#if>
			<#else>
			<#-- 普通类型 -->
			${attribute.dbName}${attribute.dbBlanks}<#if entity.attributes?size - attribute_index gt 1>,</#if>
			</#if>
		</#list>	
		) values (
		<#list entity.attributes as attribute>
			<#if attribute.entity>
			<#-- 实体类 -->
			${r"#{"}${attribute.name}.id${r"}"}${attribute.blanks}<#if entity.attributes?size - attribute_index gt 1>,</#if>
			<#else>
			<#-- 普通类型 -->
			${r"#{"}${attribute.name}${r"}"}${attribute.blanks}<#if entity.attributes?size - attribute_index gt 1>,</#if>
			</#if>
		</#list>
		)
	</insert>
	
	<insert id="batchInsert">
		insert into ${entity.dbName} (
		<#list entity.attributes as attribute>
			<#if attribute.entity>
			<#-- 实体类 -->
			${attribute.dbName}_id${attribute.dbBlanks}<#if entity.attributes?size - attribute_index gt 1>,</#if>
			<#else>
			<#-- 普通类型 -->
			${attribute.dbName}${attribute.dbBlanks}<#if entity.attributes?size - attribute_index gt 1>,</#if>
			</#if>
		</#list>	
		) 
		 <foreach collection="list" item="obj" separator="union all">
            select
        	<#list entity.attributes as attribute>
        		<#if attribute.entity>
				<#-- 实体类 -->
				${r"#{"}obj.${attribute.name}.id${r"}"}${attribute.blanks}<#if entity.attributes?size - attribute_index gt 1>,</#if>
				<#else>
				<#-- 普通类型 -->
				${r"#{"}obj.${attribute.name}${r"}"}${attribute.blanks}<#if entity.attributes?size - attribute_index gt 1>,</#if>
				</#if>
        	</#list>
         </foreach>
	</insert>
	
	<update id="update">
		update ${entity.dbName}
		<set>
		<#list entity.attributes as attribute>
			<#if attribute.name != 'id'>
			<#if attribute.entity>
			<#-- 实体类 -->
			<if test="${attribute.name} != null and ${attribute.name}.id != null and ${attribute.name}.id != ''">	
				${attribute.dbName}_id${attribute.dbBlanks} = ${r"#{"}${attribute.name}.id${r"}"}${attribute.blanks},
			</if>
			<#elseif attribute.string>
			<#-- 字符串 -->
			<if test="${attribute.name} != null">	
				${attribute.dbName}${attribute.dbBlanks} = ${r"#{"}${attribute.name}${r"}"}${attribute.blanks},
			</if>
			<#else>
			<#-- 普通类型 -->
			<if test="${attribute.name} != null">	
				${attribute.dbName}${attribute.dbBlanks} = ${r"#{"}${attribute.name}${r"}"}${attribute.blanks},
			</if>
			</#if>
			</#if>
		</#list>
		</set>
		where id =  ${r"#{"}id${r"}"}
	</update>
	
	<delete id="delete">
		delete from ${entity.dbName} where id =  ${r"#{"}id${r"}"}
	</delete>
	
	<delete id="deleteByIds">
		delete from ${entity.dbName}
		<where>
	        <foreach collection="ids" item="id" separator="or">
	            id = ${r"#{"}id${r"}"}
	        </foreach>
	    </where>
	</delete>
	
	<select id="findList" resultType="${entity.fullName}">
		select
			<include refid="columns" />
		from ${entity.dbName}
		<where>
			<include refid="querys" />
		</where>
		<if test="pagination != null and pagination.orderBy != null and pagination.orderBy != ''">
			order by ${r"${"}pagination.orderBy${r"}"}
		</if>
	</select>
	
</mapper>