package ${controller_package};

import ${entity_packege}.${entity_name};
import ${service_packege}.${table_name_hump}Service;

import com.songsy.member.common.mo.ResponseMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.songsy.member.common.base.AbstractController;

/**
* ${table_annotation} controller 控制层
* @author ${author}
* @date ${date}
*/
@RestController
@RequestMapping("/${table_name_hump?uncap_first}")
public class ${table_name_hump}Controller extends AbstractController {

    @Autowired
    private ${table_name_hump}Service ${table_name_hump?uncap_first}Service;


    @GetMapping("/list")
    public ResponseMO list () {
        return success(${table_name_hump?uncap_first}Service.list());
    }


}