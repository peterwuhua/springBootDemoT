package cn.demi.qlt.constant;

public enum QltEunm{

	//内审 流程
	QLT_00("内审计划","ZL_NS_00",null,"ZL_NS_10"),QLT_10("计划审核","ZL_NS_10","ZL_NS_00","ZL_NS_20"),
	QLT_20("汇总报告","ZL_NS_20",null,"ZL_NS_30"),QLT_30("存档记录","ZL_NS_30",null,"finish"),
	//月 记录 流程
	QLT_M_00("内审记录","ZL_NS_M_00",null,"ZL_NS_M_10"),QLT_M_10("纠正措施","ZL_NS_M_10",null,"ZL_NS_M_20"),
	QLT_M_20("验证","ZL_NS_M_20","ZL_NS_M_10","ZL_NS_M_30"),QLT_M_30("内审报告","ZL_NS_M_30",null,"ZL_NS_M_40"),
	QLT_M_40("完成","ZL_NS_M_40",null,null),
	
	GS_00("管理评审计划","ZL_GS_00",null,"GS_10"),GS_10("管理评审记录","ZL_GS_10","GS_00","GS_20"),
	GS_20("管理评审报告","ZL_GS_20",null,"GS_30"),GS_30("管理评审归档","ZL_GS_30",null,"finish"),
	
	TASK_END("完成","finish",null,null),TASK_STOP("终止","stop",null,null);
	
	public static final String PASS_Y = "1";
	public static final String PASS_N = "-1";
	public static final String PASS_S = "0";
	
	public static final String N = "N";
	public static final String Y = "Y";
	
	public static final String ITEM_FH = "符合";
	public static final String ITEM_JBFH = "基本符合";
	public static final String ITEM_BFH = "不符合";
	
	public static final String REPORT_PASS = "合格";
	public static final String REPORT_NOPASS = "不合格";
	
	private QltEunm(String name, String status, String before, String after) {
		this.name = name;
		this.status = status;
		this.before = before;
		this.after = after;
	}
	 /**
	  * 根据名称获取编号
	  * @param name
	  * @return
	  */
    public static String getStatus(String name) {  
        for (QltEunm c : QltEunm.values()) {  
            if (c.getName().equals(name)) {  
                return c.getStatus();  
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
    		for (QltEunm t : QltEunm.values()) {  
                if (t.getStatus().equals(status)) {  
                    return t.getName();  
                }  
            }  
    	}
        return null;  
    }
    /**
     * 根据状态码获取任务节点
     * @param status
     * @return
     */
    public static QltEunm getTask(String status) {
    	if(status!=null) {
    		for (QltEunm t : QltEunm.values()) {  
                if (t.getStatus().equals(status)) {  
                    return t;  
                }  
            }  
    	}
        return null;  
    }
    /**
     * 根据当前状态码和审批结果 获取下一个任务节点
     * @param status 当前状态码
     * @param audit 审批结果
     * @return
     */
    public static String getNext(String status,String audit) {
    	QltEunm task=getTask(status);
    	if(task==null) {//传入错误的状态码
    		return null;
    	}else if(audit==null||audit.equals(PASS_S)){////审批码为null返回当前任务节点
    		return task.getStatus();
    	}else if(audit.equals(PASS_Y)) {//提交下一个节点
    		return getTask(task.getAfter()).getStatus();
    	}else if(audit.equals(PASS_N)){//退回前一个节点
    		return getTask(task.getBefore()).getStatus();
    	}else {
    		return null;
    	}
    }
    /**
     * 获取前一个节点
     * @param status
     * @return
     */
    public static String getBefore(String status) {
    	if(QLT_M_40.getStatus().equals(status)) {
    		return QLT_M_30.getStatus();
    	}else if(QLT_M_30.getStatus().equals(status)) {
    		return QLT_M_20.getStatus();
    	}else if(QLT_M_20.getStatus().equals(status)) {
    		return QLT_M_10.getStatus();
    	}else if(QLT_M_10.getStatus().equals(status)) {
    		return QLT_M_00.getStatus();
    	}
    	if(TASK_END.getStatus().equals(status)) {
    		return QLT_30.getStatus();
    	}else if(QLT_30.getStatus().equals(status)) {
    		return QLT_20.getStatus();
    	}else if(QLT_20.getStatus().equals(status)) {
    		return QLT_10.getStatus();
    	}else if(QLT_10.getStatus().equals(status)) {
    		return QLT_00.getStatus();
    	}
    	
    	if(TASK_END.getStatus().equals(status)) {
    		return GS_30.getStatus();
    	}else if(GS_30.getStatus().equals(status)) {
     		return GS_20.getStatus();
     	}else if(GS_20.getStatus().equals(status)) {
     		return GS_10.getStatus();
     	}else if(GS_10.getStatus().equals(status)) {
     		return GS_00.getStatus();
     	}
    	return null;
    }
    /**
     * 获取后一个节点
     * @param status
     * @return
     */
    public static String getAfter(String status) {
    	if(QLT_M_00.getStatus().equals(status)) {
    		return QLT_M_10.getStatus();
    	}else if(QLT_M_10.getStatus().equals(status)) {
    		return QLT_M_20.getStatus();
    	}else if(QLT_M_20.getStatus().equals(status)) {
    		return QLT_M_30.getStatus();
    	}else if(QLT_M_30.getStatus().equals(status)) {
    		return QLT_M_40.getStatus();
    	}
    	if(QLT_00.getStatus().equals(status)) {
    		return QLT_10.getStatus();
    	}else if(QLT_10.getStatus().equals(status)) {
    		return QLT_20.getStatus();
    	}else if(QLT_20.getStatus().equals(status)) {
    		return QLT_30.getStatus();
    	}else if(QLT_30.getStatus().equals(status)) {
    		return TASK_END.getStatus();
    	}
    	
    	if(GS_00.getStatus().equals(status)) {
     		return GS_10.getStatus();
     	}else if(GS_10.getStatus().equals(status)) {
     		return GS_20.getStatus();
     	}else if(GS_20.getStatus().equals(status)) {
     		return GS_30.getStatus();
     	}else if(GS_30.getStatus().equals(status)) {
     		return TASK_END.getStatus();
     	}
    	
    	return null;
    }
	/**
	 * 节点名称
	 */
    private String name;
	/**
	 * 节点状态
	 */
    private String status;
    /**
     * 退回到节点
     */
    private String before;
    /**
     * 提交下一个节点
     */
    private String after;
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
	public String getBefore() {
		return before;
	}
	public void setBefore(String before) {
		this.before = before;
	}
	public String getAfter() {
		return after;
	}
	public void setAfter(String after) {
		this.after = after;
	}
}
