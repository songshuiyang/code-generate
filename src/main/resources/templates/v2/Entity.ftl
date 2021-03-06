package ${package_name};
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.math.BigDecimal;
import lombok.Data;

/**
* ${table_annotation} 实体类
* @author ${author}
* @date ${date}
*/
@Data
@TableName("${table_name}")
public class ${entity_name} extends BaseDO<${entity_name}> {

<#if entity_column?exists>
  <#list entity_column as entity>
    /**
     * ${entity.columnComment!}
     */
    private ${entity.javaType!} ${entity.changeColumnName?uncap_first};

  </#list>
</#if>
<#--get set 生成-->

<#--<#if entity_column?exists>-->
<#--  <#list entity_column as entity>-->

<#--    public ${entity.javaType!} get${entity.changeColumnName}() {-->
<#--        return this.${entity.changeColumnName?uncap_first};-->
<#--    }-->

<#--    public void set${entity.changeColumnName}(${entity.javaType!} ${entity.changeColumnName?uncap_first}) {-->
<#--        this.${entity.changeColumnName?uncap_first} = ${entity.changeColumnName?uncap_first};-->
<#--    }-->

<#--  </#list>-->
<#--</#if>-->

}