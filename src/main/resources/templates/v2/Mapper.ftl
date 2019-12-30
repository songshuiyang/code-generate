<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${mapper_packege}.${table_name_hump}Mapper" >
    <resultMap id="BaseResultMap" type="${package_name}.${entity_name}" >
<#if entity_column?exists>
    <#list entity_column as entity>
        <#--生成主键 id-->
      <#if entity.columnName == 'id' && entity.columnType == 'INT'>
        <id column="id" property="id" jdbcType="INTEGER" />
      <#elseif entity.columnName == 'id' && entity.columnType == 'VARCHAR'>
        <id column="id" property="id" jdbcType="VARCHAR" />
      <#else>
        <result column="${entity.columnName}" property="${entity.changeColumnName?uncap_first}" jdbcType="${entity.columnType}" />
      </#if>
    </#list>
</#if>
    </resultMap>
    <sql id="Base_Column_List" >
    <#if entity_column?exists>
        <#list entity_column as entity>
            <#if entity_has_next>
        ${entity.columnName},
            <#else>
        ${entity.columnName}
            </#if>
        </#list>
    </#if>
    </sql>


    <!--分页查询-->
    <select id="findPageList" parameterType="${package_name}.${entity_name}" resultType="${package_name}.${entity_name}">
        SELECT
        <include refid="Base_Column_List"/>
        FROM
        ${table_name}
        <where>
    <#if entity_column?exists>
    <#list entity_column as entity>
    <#--字符类型使用like-->
        <#if entity.javaType == 'String'>
            <if test="${entity.changeColumnName?uncap_first} != null and ${entity.changeColumnName?uncap_first} !=''" >
                AND ${entity.columnName} LIKE CONCAT('%',${char_1}{${entity.changeColumnName?uncap_first},jdbcType=${entity.columnType}},'%')
            </if>
        <#else>
            <if test="${entity.changeColumnName?uncap_first} != null" >
                AND ${entity.columnName} = ${char_1}{${entity.changeColumnName?uncap_first},jdbcType=${entity.columnType}}
            </if>
        </#if>
    </#list>
    </#if>
        </where>
    </select>
</mapper>