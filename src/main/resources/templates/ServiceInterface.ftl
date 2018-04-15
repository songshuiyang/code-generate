package ${package_name};

import ${entity_packege}.${entity_name};

/**
* ${table_annotation} service接口
* @author ${author}
* @date ${date}
*/
public interface ${entity_name}Service extends extends BaseService<${entity_name},${id_java_type}>{
    int deleteByPrimaryKey(${id_java_type} id);

    int insert(${entity_name} record);

    int insertSelective(${entity_name} record);

    User selectByPrimaryKey(${id_java_type} id);

    int updateByPrimaryKeySelective(${entity_name} record);

    int updateByPrimaryKey(${entity_name} record);
}