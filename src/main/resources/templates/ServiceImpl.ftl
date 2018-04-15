package ${package_name};

import ${entity_packege}.${entity_name};

/**
* ${table_annotation} service接口
* @author ${author}
* @date ${date}
*/
@Service
public class ${entity_name}ServiceImpl extends BaseServiceImpl<${entity_name},${id_java_type}> implements ${entity_name}Service{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ${entity_name}Mapper mapper;

    public int deleteByPrimaryKey(${id_java_type} id){
        return mapper.deleteByPrimaryKey(id);
    }

    public int insert(${entity_name} record){
        return mapper.insert(record);
    }

    public int insertSelective(${entity_name} record){
        return mapper.insertSelective(record);
    }

    public User selectByPrimaryKey(${id_java_type} id){
        return mapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(${entity_name} record){
        return mapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(${entity_name} record){
        return mapper.updateByPrimaryKey(record);
    }
}