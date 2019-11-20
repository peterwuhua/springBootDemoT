package cn.core.framework.utils.code;

import org.apache.commons.lang3.StringUtils;

import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.code.annotation.Module;
import cn.core.framework.utils.code.bean.EntityInfoBean;

public class LoadEntityInfo {
	public static EntityInfoBean load(Class<? extends Object> entityCls) {
		EntityInfoBean entityInfoBean = new EntityInfoBean();

		Module module = entityCls.getAnnotation(Module.class);
		String moduleStr = "";
		if (module == null) {
			moduleStr = StrUtils.toLowerCaseOfFirst(entityCls.getSimpleName());
		} else {
			if (StringUtils.isBlank(module.value())) {
				moduleStr = StrUtils.toLowerCaseOfFirst(entityCls
						.getSimpleName());
			} else {
				moduleStr = module.value();
			}

		}

		entityInfoBean.setModule(moduleStr);
		entityInfoBean.setHttpRequestMapping("/" + moduleStr.replace(".", "/"));

		Package entityPackage = entityCls.getPackage();
		String entityPackageName = entityPackage.getName();

		entityInfoBean.setDaoInterfacePackageName(entityPackageName.replace(
				"po", "dao"));
		entityInfoBean.setDaoImplPackageName(entityInfoBean
				.getDaoInterfacePackageName() + ".impl");
		entityInfoBean.setServiceInterfacePackageName(entityPackageName
				.replace("po", "service"));
		entityInfoBean.setServiceImplPackageName(entityInfoBean
				.getServiceInterfacePackageName() + ".impl");
		entityInfoBean.setControllerPackageName(entityPackageName.replace("po",
				"action"));
		entityInfoBean.setEntityName(entityCls.getName());
		entityInfoBean.setVoPackageName(entityCls.getPackage().getName()
				.replace("po", "vo"));
		entityInfoBean.setSimpleEntityName(entityCls.getSimpleName());
		String simpleEntityName = entityCls.getSimpleName();
		String start = simpleEntityName.substring(0, 1);
		entityInfoBean.setEntityLoverCaseName(simpleEntityName.replaceFirst(
				start, start.toLowerCase()));

		return entityInfoBean;
	}

}
