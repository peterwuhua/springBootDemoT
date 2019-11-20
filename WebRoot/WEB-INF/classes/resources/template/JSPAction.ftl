package ${controllerPackageName};

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import ${voPackageName}.${simpleEntityName}Vo;
import cn.core.framework.common.service.IBaseService;
import ${serviceInterfacePackageName}.I${simpleEntityName}Service;
import cn.core.framework.common.action.BaseAction;

@Controller("${module}Action")
@RequestMapping("${httpRequestMapping}")
public class ${simpleEntityName}Action extends BaseAction<${simpleEntityName}Vo> {
	final String VIEW_PATH = "${httpRequestMapping}/${entityLoverCaseName}";
	@Autowired private I${simpleEntityName}Service ${entityLoverCaseName}Service;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<${simpleEntityName}Vo> baseService() {
		return ${entityLoverCaseName}Service;
	}
}