package ${daoImplPackageName};

import org.springframework.stereotype.Repository;
import ${daoInterfacePackageName}.I${simpleEntityName}Dao;
import ${entityName};
import cn.core.framework.common.dao.BaseDaoImpl;

@Repository("${module}Dao")
public class ${simpleEntityName}DaoImpl extends BaseDaoImpl<${simpleEntityName}> implements I${simpleEntityName}Dao {
}
