<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackageName}.${mapperJavaName}">

	<sql id="columns">
	<#list attributes as attribute>
		<#if attribute.entity>
		<#-- 实体类 -->
		`${attribute.dbName}_id`${attribute.dbBlanks} as "${attribute.javaName}.id"${attribute.javaBlanks}<#if attributes?size - attribute_index gt 1>,</#if>
		<#else>
		<#-- 普通类型 -->
		`${attribute.dbName}`${attribute.dbBlanks} as "${attribute.javaName}${attribute.javaBlanks}"<#if attributes?size - attribute_index gt 1>,</#if>
		</#if>
	</#list>
	</sql>
	
	<sql id="querys">
	<#list attributes as attribute>
		<#if attribute.entity>
		<#-- 实体类 -->
		<if test="${attribute.javaName} != null and ${attribute.javaName}.id != null and ${attribute.javaName}.id != ''">	
			and `${attribute.dbName}_id`${attribute.dbBlanks} = ${r"#{"}${attribute.javaName}.id${r"}"}${attribute.javaBlanks}
		</if>
		<#elseif attribute.string>
		<#-- 字符串 -->
		<if test="${attribute.javaName} != null and ${attribute.javaName} != ''">	
			and `${attribute.dbName}`${attribute.dbBlanks} = ${r"#{"}${attribute.javaName}${r"}"}${attribute.javaBlanks}
		</if>
		<#else>
		<#-- 非字符串普通类型 -->
		<if test="${attribute.javaName} != null">	
			and `${attribute.dbName}`${attribute.dbBlanks} = ${r"#{"}${attribute.javaName}${r"}"}${attribute.javaBlanks}
		</if>
		</#if>
	</#list>
	</sql>
	
	<#-- 根据主键查询 -->
	<select id="get" resultType="${entityPackageName}.${entityJavaName}">
		select 
			<include refid="columns" />
		from `${dbName}`
		where id =  ${r"#{"}id${r"}"}
	</select>
	
	<insert id="insert">
		insert into `${dbName}` (
		<#list attributes as attribute>
			<#if attribute.entity>
			<#-- 实体类 -->
			`${attribute.dbName}_id`${attribute.dbBlanks}<#if attributes?size - attribute_index gt 1>,</#if>
			<#else>
			<#-- 普通类型 -->
			`${attribute.dbName}`${attribute.dbBlanks}<#if attributes?size - attribute_index gt 1>,</#if>
			</#if>
		</#list>	
		) values (
		<#list attributes as attribute>
			<#if attribute.entity>
			<#-- 实体类 -->
			${r"#{"}${attribute.javaName}.id${r"}"}${attribute.javaBlanks}<#if attributes?size - attribute_index gt 1>,</#if>
			<#else>
			<#-- 普通类型 -->
			${r"#{"}${attribute.javaName}${r"}"}${attribute.javaBlanks}<#if attributes?size - attribute_index gt 1>,</#if>
			</#if>
		</#list>
		)
	</insert>
	
	<insert id="batchInsert">
		insert into `${dbName}` (
		<#list attributes as attribute>
			<#if attribute.entity>
			<#-- 实体类 -->
			`${attribute.dbName}_id`${attribute.dbBlanks}<#if attributes?size - attribute_index gt 1>,</#if>
			<#else>
			<#-- 普通类型 -->
			`${attribute.dbName}`${attribute.dbBlanks}<#if attributes?size - attribute_index gt 1>,</#if>
			</#if>
		</#list>	
		) 
		 <foreach collection="list" item="obj" separator="union all">
            select
        	<#list attributes as attribute>
        		<#if attribute.entity>
				<#-- 实体类 -->
				${r"#{"}obj.${attribute.javaName}.id${r"}"}${attribute.javaBlanks}<#if attributes?size - attribute_index gt 1>,</#if>
				<#else>
				<#-- 普通类型 -->
				${r"#{"}obj.${attribute.javaName}${r"}"}${attribute.javaBlanks}<#if attributes?size - attribute_index gt 1>,</#if>
				</#if>
        	</#list>
         </foreach>
	</insert>
	
	<update id="update">
		update `${dbName}`
		<set>
		<#list attributes as attribute>
			<#if attribute.javaName != 'id'>
			<#if attribute.entity>
			<#-- 实体类 -->
			<if test="${attribute.javaName} != null and ${attribute.javaName}.id != null and ${attribute.javaName}.id != ''">	
				`${attribute.dbName}_id`${attribute.dbBlanks} = ${r"#{"}${attribute.javaName}.id${r"}"}${attribute.javaBlanks},
			</if>
			<#elseif attribute.string>
			<#-- 字符串 -->
			<if test="${attribute.javaName} != null">	
				`${attribute.dbName}`${attribute.dbBlanks} = ${r"#{"}${attribute.javaName}${r"}"}${attribute.javaBlanks},
			</if>
			<#else>
			<#-- 普通类型 -->
			<if test="${attribute.javaName} != null">	
				`${attribute.dbName}`${attribute.dbBlanks} = ${r"#{"}${attribute.javaName}${r"}"}${attribute.javaBlanks},
			</if>
			</#if>
			</#if>
		</#list>
		</set>
		where id =  ${r"#{"}id${r"}"}
	</update>
	
	<delete id="delete">
		delete from `${dbName}` where id =  ${r"#{"}id${r"}"}
	</delete>
	
	<delete id="deleteByIds">
		delete from `${dbName}`
		<where>
	        <foreach collection="ids" item="id" separator="or">
	            id = ${r"#{"}id${r"}"}
	        </foreach>
	    </where>
	</delete>
	
	<select id="findList" resultType="${entityPackageName}.${entityJavaName}">
		select
			<include refid="columns" />
		from `${dbName}`
		<where>
			<include refid="querys" />
		</where>
		<if test="orderByStr != null and orderByStr != ''">
			order by ${r"${"}orderByStr${r"}"}
		</if>
	</select>
	
</mapper>