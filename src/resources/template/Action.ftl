package ${controllerPackageName};

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ${voPackageName}.${simpleEntityName}Vo;
import cn.core.framework.common.action.RestAction;

@Controller("${module}Action")
@RequestMapping("${httpRequestMapping}")
public class ${simpleEntityName}Action extends RestAction<${simpleEntityName}Vo> {
}