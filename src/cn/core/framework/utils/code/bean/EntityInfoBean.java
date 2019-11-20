package cn.core.framework.utils.code.bean;

public class EntityInfoBean {
	private String entityLoverCaseName;
	private String simpleEntityName;
	private String entityName;
	private String daoInterfacePackageName;
	private String daoImplPackageName;
	private String serviceInterfacePackageName;
	private String serviceImplPackageName;
	private String controllerPackageName;
	private String voPackageName;
	private String module;
	private String httpRequestMapping;

	public String getSimpleEntityName() {
		return simpleEntityName;
	}

	public void setSimpleEntityName(String simpleEntityName) {
		this.simpleEntityName = simpleEntityName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getDaoInterfacePackageName() {
		return daoInterfacePackageName;
	}

	public void setDaoInterfacePackageName(String daoInterfacePackageName) {
		this.daoInterfacePackageName = daoInterfacePackageName;
	}

	public String getServiceInterfacePackageName() {
		return serviceInterfacePackageName;
	}

	public void setServiceInterfacePackageName(
			String serviceInterfacePackageName) {
		this.serviceInterfacePackageName = serviceInterfacePackageName;
	}

	public String getServiceImplPackageName() {
		return serviceImplPackageName;
	}

	public void setServiceImplPackageName(String serviceImplPackageName) {
		this.serviceImplPackageName = serviceImplPackageName;
	}

	public String getControllerPackageName() {
		return controllerPackageName;
	}

	public void setControllerPackageName(String controllerPackageName) {
		this.controllerPackageName = controllerPackageName;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getHttpRequestMapping() {
		return httpRequestMapping;
	}

	public void setHttpRequestMapping(String httpRequestMapping) {
		this.httpRequestMapping = httpRequestMapping;
	}

	public String getDaoImplPackageName() {
		return daoImplPackageName;
	}

	public void setDaoImplPackageName(String daoImplPackageName) {
		this.daoImplPackageName = daoImplPackageName;
	}

	public String getEntityLoverCaseName() {
		return entityLoverCaseName;
	}

	public void setEntityLoverCaseName(String entityLoverCaseName) {
		this.entityLoverCaseName = entityLoverCaseName;
	}

	public String getVoPackageName() {
		return voPackageName;
	}

	public void setVoPackageName(String voPackageName) {
		this.voPackageName = voPackageName;
	}

}
