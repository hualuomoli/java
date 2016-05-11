<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${fullName}">

	<sql id="columns">
	<#list entity.attributes as attribute>
		${attribute.dbName}${attribute.dbBlanks} as "${attribute.name}"<#if entity.attributes?size - attribute_index gt 1>,</#if>
	</#list>
	</sql>
	
	<sql id="querys">
	<#list entity.attributes as attribute>
		<if test="${attribute.name} != null<#if attribute.string> and ${attribute.name} != ''</#if>">	
			${attribute.dbName}${attribute.dbBlanks} = ${r"#{"}${attribute.name}${r"}"}${attribute.blanks}
		</if>
	</#list>
	</sql>
	
	<select id="get" resultType="${entity.fullName}">
		select 
			<include refid="columns" />
		from ${entity.dbName}
		where id =  ${r"#{"}id${r"}"}
	</select>
	
	<insert id="insert">
		insert into ${entity.dbName} (
		<#list entity.attributes as attribute>
			${attribute.dbName}${attribute.dbBlanks}<#if entity.attributes?size - attribute_index gt 1>,</#if>
		</#list>	
		) values (
		<#list entity.attributes as attribute>
			${r"#{"}${attribute.name}${r"}"}${attribute.blanks}<#if entity.attributes?size - attribute_index gt 1>,</#if>
		</#list>
		)
	</insert>
	
	<insert id="batchInsert">
		insert into ${entity.dbName} (
		<#list entity.attributes as attribute>
			${attribute.dbName}${attribute.dbBlanks}<#if entity.attributes?size - attribute_index gt 1>,</#if>
		</#list>	
		) 
		 <foreach collection="list" item="obj" separator="union all">
            select
        	<#list entity.attributes as attribute>
        		${r"#{"}obj.${attribute.name}${r"}"}${attribute.blanks}<#if entity.attributes?size - attribute_index gt 1>,</#if>
        	</#list>
         </foreach>
	</insert>
	
	<update id="update">
		update ${entity.dbName}
		<set>
		<#list entity.attributes as attribute>
			<#if attribute.name != 'id'>
			<if test="${attribute.name} != null<#if attribute.string> and ${attribute.name} != ''</#if>">	
				${attribute.dbName}${attribute.dbBlanks} = ${r"#{"}${attribute.name}${r"}"}${attribute.blanks},
			</if>
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