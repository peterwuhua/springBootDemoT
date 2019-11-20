package cn.core.framework.constant;

/**
 * 质控流程
 * @author QuJunLong
 *
 */
public enum EunmZkTask {

	TASK_00("计划制定","ZK_00"),TASK_10("计划审核","ZK_10"),
	TASK_20("忙样配置","ZK_20"),TASK_30("任务下达","ZK_30"),
	TASK_40("任务分配","ZK_40"),
	TASK_50("数据复核","ZK_50"),
	TASK_60("质控评价","ZK_60"),
	TASK_100("完成","100"),
	ITEM_0("项目初始化","ZK_0"),
	ITEM_1("样品检测","ZK_1");
	public static final String PASS_Y = "通过";
	public static final String PASS_N = "退回";
	public static final String PASS_S = "保存";
	public static final String PASS_T = "提交";
	
	/**
	 * 节点名称
	 */
    private String name;
	/**
	 * 节点状态
	 */
    private String status;
    
    public static String getBefore(String status) {
    	if(TASK_100.getStatus().equals(status)) {
    		return TASK_60.getStatus();
    	}else if(TASK_60.getStatus().equals(status)) {
    		return TASK_50.getStatus();
    	}else if(TASK_50.getStatus().equals(status)) {
    		return TASK_40.getStatus();
    	}else if(TASK_40.getStatus().equals(status)) {
    		return TASK_30.getStatus();
    	}else if(TASK_30.getStatus().equals(status)) {
    		return TASK_20.getStatus();
    	}else if(TASK_20.getStatus().equals(status)) {
    		return TASK_10.getStatus();
    	}else if(TASK_10.getStatus().equals(status)) {
    		return TASK_00.getStatus();
    	}
    	return null;
    }
    public static String getAfter(String status) {
    	if(TASK_00.getStatus().equals(status)) {
    		return TASK_10.getStatus();
    	}else if(TASK_10.getStatus().equals(status)) {
    		return TASK_20.getStatus();
    	}else if(TASK_20.getStatus().equals(status)) {
    		return TASK_30.getStatus();
    	}else if(TASK_30.getStatus().equals(status)) {
    		return TASK_40.getStatus();
    	}else if(TASK_40.getStatus().equals(status)) {
    		return TASK_50.getStatus();
    	}else if(TASK_50.getStatus().equals(status)) {
    		return TASK_60.getStatus();
    	}else if(TASK_60.getStatus().equals(status)) {
    		return TASK_100.getStatus();
    	}
    	return null;
    }
    // 构造方法  
    private EunmZkTask(String name,String status) {  
        this.name = name;  
        this.status = status; 
    }
    /**
     * 根据状态码获取任务节点
     * @param status
     * @return
     */
    public static EunmZkTask getTask(String status) {
    	if(status!=null) {
    		for (EunmZkTask t : EunmZkTask.values()) {  
                if (t.getStatus().equals(status)) {  
                    return t;  
                }  
            }  
    	}
        return null;  
    }
    /**
     * 根据状态码获取任务名称
     * @param status
     * @return
     */
    public static String getName(String status) {
    	if(status!=null) {
    		for (EunmZkTask t : EunmZkTask.values()) {  
                if (t.getStatus().equals(status)) {  
                    return t.getName();  
                }  
            }  
    	}
        return null;  
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
