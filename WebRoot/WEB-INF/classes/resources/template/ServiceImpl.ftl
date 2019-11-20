package ${serviceImplPackageName};

import org.springframework.stereotype.Service;
import ${entityName};
import ${voPackageName}.${simpleEntityName}Vo;
import ${serviceInterfacePackageName}.I${simpleEntityName}Service;
import cn.core.framework.common.service.BaseServiceImpl;

@Service("${module}Service")
public class ${simpleEntityName}ServiceImpl extends BaseServiceImpl<${simpleEntityName}Vo,${simpleEntityName}> implements
		I${simpleEntityName}Service {
}
