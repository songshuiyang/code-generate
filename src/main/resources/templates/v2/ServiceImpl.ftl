package ${serviceImpl_packege};

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${entity_packege}.${entity_name};
import ${mapper_packege}.${table_name_hump}Mapper;
import ${service_packege}.${table_name_hump}Service;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
* ${table_annotation} service接口
* @author ${author}
* @date ${date}
*/
@Slf4j
@Service
public class ${table_name_hump}ServiceImpl extends ServiceImpl<${table_name_hump}Mapper,${entity_name}> implements ${table_name_hump}Service {

}