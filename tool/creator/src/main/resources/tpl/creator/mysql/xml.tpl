<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.base.mapper.Base${javaName}Mapper">

  <sql id="columns">
  <#list table.columns as column>
    <#if column.entity>
    <#-- 实体类 -->
    `${column.dbName}`${column.dbBlanks} as "${column.javaName}.${column.relation}"${column.javaBlanks}<#if table.columns?size - column_index gt 1>,</#if>
    <#else>
    <#-- 普通类型 -->
    `${column.dbName}`${column.dbBlanks} as "${column.javaName}"${column.javaBlanks}<#if table.columns?size - column_index gt 1>,</#if>
    </#if>
  </#list>
  </sql>
  
  <sql id="querys">
  <#list table.columns as column>
    <#if column.entity>
    <#-- 实体类 -->
    <if test="${column.javaName} != null and ${column.javaName}.${column.relation} != null and ${column.javaName}.${column.relation} != ''"> 
      and `${column.dbName}`${column.dbBlanks} = ${r"#{"}${column.javaName}.${column.relation}${r"}"}${column.javaBlanks}
    </if>
    <#elseif column.string>
    <#-- 字符串 -->
    <if test="${column.javaName} != null and ${column.javaName} != ''"> 
      and `${column.dbName}`${column.dbBlanks} = ${r"#{"}${column.javaName}${r"}"}${column.javaBlanks}
    </if>
    <#else>
    <#-- 非字符串普通类型 -->
    <if test="${column.javaName} != null"> 
      and `${column.dbName}`${column.dbBlanks} = ${r"#{"}${column.javaName}${r"}"}${column.javaBlanks}
    </if>
    </#if>
  </#list>
	<!-- 比较查询 -->
  <#list table.queryColumns as queryColumn>
  	<#if queryColumn.array>
    <#-- array -->
    <if test="${queryColumn.javaName} != null"> 
      and
      <foreach collection="${queryColumn.javaName}" open="(" close=")" separator="or" item="data">
    		`${queryColumn.dbName}` = ${r"#{"}data${r"}"}
    	</foreach>
    </if>
    <#elseif queryColumn.string>
    <#-- 字符串 -->
    <if test="${queryColumn.javaName} != null and ${queryColumn.javaName} != ''"> 
      and `${queryColumn.dbName}`${queryColumn.dbBlanks} ${queryColumn.operator} ${r"#{"}${queryColumn.javaName}${r"}"}${queryColumn.javaBlanks}
    </if>
    <#else>
    <#-- 非字符串普通类型 -->
    <if test="${queryColumn.javaName} != null"> 
      and `${queryColumn.dbName}`${queryColumn.dbBlanks} ${queryColumn.operator} ${r"#{"}${queryColumn.javaName}${r"}"}${queryColumn.javaBlanks}
    </if>
    </#if>
  </#list>
  </sql>
  
  <#-- 根据主键查询 -->
  <select id="get" resultType="${packageName}.base.entity.Base${javaName}">
    select 
      <include refid="columns" />
    from `${table.dbName}`
    where id =  ${r"#{"}id${r"}"}
  </select>
  
  <insert id="insert">
    insert into `${table.dbName}` (
    <#list table.columns as column>
      <#if column.entity>
      <#-- 实体类 -->
      `${column.dbName}`${column.dbBlanks}<#if table.columns?size - column_index gt 1>,</#if>
      <#else>
      <#-- 普通类型 -->
      `${column.dbName}`${column.dbBlanks}<#if table.columns?size - column_index gt 1>,</#if>
      </#if>
    </#list>  
    ) values (
    <#list table.columns as column>
      <#if column.entity>
      <#-- 实体类 -->
      ${r"#{"}${column.javaName}.${column.relation}${r"}"}${column.javaBlanks}<#if table.columns?size - column_index gt 1>,</#if>
      <#else>
      <#-- 普通类型 -->
      ${r"#{"}${column.javaName}${r"}"}${column.javaBlanks}<#if table.columns?size - column_index gt 1>,</#if>
      </#if>
    </#list>
    )
  </insert>
  
  <insert id="batchInsert">
    insert into `${table.dbName}` (
    <#list table.columns as column>
      <#if column.entity>
      <#-- 实体类 -->
      `${column.dbName}`${column.dbBlanks}<#if table.columns?size - column_index gt 1>,</#if>
      <#else>
      <#-- 普通类型 -->
      `${column.dbName}`${column.dbBlanks}<#if table.columns?size - column_index gt 1>,</#if>
      </#if>
    </#list>  
    ) 
     <foreach collection="list" item="obj" separator="union all">
            select
          <#list table.columns as column>
            <#if column.entity>
        <#-- 实体类 -->
        ${r"#{"}obj.${column.javaName}.${column.relation}${r"}"}${column.javaBlanks}<#if table.columns?size - column_index gt 1>,</#if>
        <#else>
        <#-- 普通类型 -->
        ${r"#{"}obj.${column.javaName}${r"}"}${column.javaBlanks}<#if table.columns?size - column_index gt 1>,</#if>
        </#if>
          </#list>
         </foreach>
  </insert>
  
  <update id="update">
    update `${table.dbName}`
    <set>
    <#list table.columns as column>
      <#if column.javaName != 'id'>
      <#if column.entity>
      <#-- 实体类 -->
      <if test="${column.javaName} != null and ${column.javaName}.${column.relation} != null and ${column.javaName}.${column.relation} != ''"> 
        `${column.dbName}`${column.dbBlanks} = ${r"#{"}${column.javaName}.${column.relation}${r"}"}${column.javaBlanks},
      </if>
      <#elseif column.string>
      <#-- 字符串 -->
      <if test="${column.javaName} != null"> 
        `${column.dbName}`${column.dbBlanks} = ${r"#{"}${column.javaName}${r"}"}${column.javaBlanks},
      </if>
      <#else>
      <#-- 普通类型 -->
      <if test="${column.javaName} != null"> 
        `${column.dbName}`${column.dbBlanks} = ${r"#{"}${column.javaName}${r"}"}${column.javaBlanks},
      </if>
      </#if>
      </#if>
    </#list>
    </set>
    where id =  ${r"#{"}id${r"}"}
  </update>
  
  <delete id="delete">
    delete from `${table.dbName}` where id =  ${r"#{"}id${r"}"}
  </delete>
  
  <delete id="deleteByIds">
    delete from `${table.dbName}`
    <where>
          <foreach collection="ids" item="id" separator="or">
              id = ${r"#{"}id${r"}"}
          </foreach>
      </where>
  </delete>
  
  <select id="findList" resultType="${packageName}.base.entity.Base${javaName}">
    select
      <include refid="columns" />
    from `${table.dbName}`
    <where>
      <include refid="querys" />
    </where>
  </select>
  
</mapper>