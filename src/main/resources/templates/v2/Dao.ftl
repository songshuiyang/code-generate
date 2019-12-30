package ${package_name};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${entity_packege}.${entity_name};
import java.util.List;

/**
* ${table_annotation} Mapper接口
* @author ${author}
* @date ${date}
*/
public interface ${table_name_hump}Mapper extends BaseMapper<${entity_name}>{

    List<${entity_name}> findPageList (${entity_name} searchDO);

}