package ${package_name}.entity;
import javax.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* ${table_annotation} 实体类
* @author ${author}
* @date ${date}
*/
@Table(name="${table_name}")
@Getter
@Setter
@ToString
public class ${model_name} extends BaseEntity {

<#if model_column?exists>
    <#list model_column as model>
        /**
        * ${model.columnComment!}
        */
        <#if (model.columnType = 'VARCHAR' || model.columnType = 'TEXT')>
        <#--@Column(name = "${model.columnName}",columnDefinition = "VARCHAR")-->
        private String ${model.changeColumnName?uncap_first};
        </#if>
        <#if model.columnType = 'TIMESTAMP' || model.columnType = 'DATETIME'>
        private Date ${model.changeColumnName?uncap_first};
        </#if>
        <#if model.columnType = 'INT'>
        private Integer ${model.changeColumnName?uncap_first};
        </#if>
        <#if model.columnType = 'BIT'>
        private Boolean ${model.changeColumnName?uncap_first};
        </#if>
    </#list>
</#if>
<#--get set 生成-->

<#if model_column?exists>
    <#list model_column as model>
        <#if (model.columnType = 'VARCHAR' || model.columnType = 'TEXT')>
        public String get${model.changeColumnName}() {
            return this.${model.changeColumnName?uncap_first};
        }

        public void set${model.changeColumnName}(String ${model.changeColumnName?uncap_first}) {
            this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
        }

        </#if>
        <#if model.columnType = 'TIMESTAMP' >
        public Date get${model.changeColumnName}() {
            return this.${model.changeColumnName?uncap_first};
        }

        public void set${model.changeColumnName}(Date ${model.changeColumnName?uncap_first}) {
            this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
        }

        </#if>
    </#list>
</#if>

}