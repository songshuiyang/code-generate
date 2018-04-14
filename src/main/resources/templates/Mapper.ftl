<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${mapper_packege??}.${entity_name??}Mapper" >
    <resultMap id="BaseResultMap" type="${package_name??}.${entity_name??}" >
<#if entity_column?exists>
    <#list entity_column as entity>
        <#--生成主键 id-->
      <#if entity.columnName == 'id' && entity.columnType == 'INT'>
        <id column="id" property="id" jdbcType="INTEGER" />
      <#elseif entity.columnName == 'id' && entity.columnType == 'VARCHAR'>
        <id column="id" property="id" jdbcType="VARCHAR" />
      <#else>
        <result column="${entity.columnName??}" property="${entity.changeColumnName?uncap_first}" jdbcType="${entity.columnType??}" />
      </#if>
    </#list>
</#if>
    </resultMap>
    <sql id="Base_Column_List" >
    <#if entity_column?exists>
        <#list entity_column as entity>
            <#if entity_has_next>
        ${entity.columnName??},
            <#else>
        ${entity.columnName??}
            </#if>
        </#list>
    </#if>
    </sql>

</mapper>