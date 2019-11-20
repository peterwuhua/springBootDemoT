package ${serviceInterfacePackageName};

import org.springframework.transaction.annotation.Transactional;

import ${voPackageName}.${simpleEntityName}Vo;
import cn.core.framework.common.service.IBaseService;

@Transactional
public interface I${simpleEntityName}Service extends IBaseService<${simpleEntityName}Vo> {
	
}
