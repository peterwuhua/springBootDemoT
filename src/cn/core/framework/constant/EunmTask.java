package cn.core.framework.constant;

public enum EunmTask {

	PROJECT_LX("项目立项","PJ_00",null,"PJ_10"),PROJECT_ZP("任务指派","PJ_10",null,"PJ_20"),
	PROJECT_TK("现场踏勘","PJ_20",null,"PJ_30"),PROJECT_BZ("方案编制","PJ_30","PJ_00","PJ_40"),
	PROJECT_PS("合同评审","PJ_40","PJ_30","PJ_50"),PROJECT_QD("合同签订","PJ_50",null,"PJ_60"),
	PROJECT_HT("合同管理","PJ_51",null,null),//只供合同管理下提示用，不参与流程
	PROJECT_JX("执行中","PJ_60",null,"finish"),
	
	TASK_DJ("任务登记","RW_00",null,"RW_01"),TASK_AP("采样安排","RW_01","RW_00","RW_02"),
	TASK_ZB("采样准备","RW_02",null,"RW_03"),TASK_XC("现场采样","RW_03",null,"RW_04"),
	TASK_JJ("样品交接","RW_04","RW_03","RW_05"),TASK_XD("任务下达","RW_05",null,"RW_06"),
	TASK_HZ("数据审核","RW_06",null,"RW_08"),
	
	ITEM_FP("任务分配","IT_00",null,"IT_01"),ITEM_LR("数据录入","IT_01",null,"IT_02"),
	ITEM_JY("数据校核","IT_02","IT_01","IT_03"),ITEM_HZ("数据审核","IT_03","IT_01","finish"),
	 
	TASK_BZ("报告编制","RW_10","IT_01","RW_11"),TASK_FH("报告复核","RW_11","RW_10","RW_12"),
	TASK_SH("报告审核","RW_12","RW_10","RW_13"),TASK_PZ("报告签发","RW_13","RW_10","RW_14"),
	TASK_FF("报告发放","RW_14",null,"RW_15"),TASK_GD("报告归档","RW_15",null,"finish"),
	
	TASK_B_BZ("报表编制","RW_20","RW_05","RW_21"),TASK_B_FH("报表复核","RW_21","RW_20","RW_22"),
	TASK_B_SH("报表审核","RW_22","RW_20","RW_23"),TASK_B_QF("报表签发","RW_23","RW_20","RW_24"),
	TASK_B_GD("报表归档","RW_24",null,"finish"),
	
	TASK_END("完成","finish",null,null),TASK_STOP("终止","stop",null,null),
	//行政模块(old)
	GS_SQ("行政申请","GS_00",null,"GS_10"),GS_SH("行政审核","GS_10","GS_00","finish"),GS_CS("抄送查看","GS_20",null,null),
	
	//文件流程
	DT_SQ("文件登记","DT_00",null,"DT_10"),DT_QF("文件签发","DT_10","DT_00","DT_20"),DT_XF("文件下发","DT_20",null,"DT_30"),DT_CK("文件查看","DT_30",null,"finish"),
	//归档管理流程
	GD_SQ("归档申请","GD_00",null,"GD_10"),GD_SH("归档审核","GD_10",null,"GD_20"),GD_QR("归档确认","GD_20",null,"GD_30"),GD_CY("归档查阅","GD_30",null,"finish"),
	//车辆管理
	CL_SQ("车辆使用申请","CL_00",null,"CL_10"),CL_SH("车辆使用审核","CL_10","CL_00","finish"),
	//考勤管理
	QJ_SQ("请假申请","QJ_00",null,"QJ_10"),QJ_AD("请假审核","QJ_10","QJ_00","QJ_20"),QJ_SH("副总审核","QJ_20","QJ_00","QJ_30"),
	QJ_SP("总经理审核","QJ_30","QJ_00","QJ_40"),QJ_XJ("销假","QJ_40",null,"finish"),
	//补卡
	BK_SQ("补卡申请","BK_00",null,"BK_10"),BK_SH("补卡审核","BK_10","BK_00","finish"),
	//会议
	HW_SQ("会务申请","HW_00",null,"HW_10"),HW_SH("会务审核","HW_10","HW_00","HW_20"),HW_CY("会务查阅","HW_20",null,"finish"),	
	//行政管理-办公用品
	BG_SQ("办公用品申请","BG_00",null,"BG_10"),BG_BMSP("主管(批准/驳回)","BG_10",null,"BG_20"),BG_FZSP("分管副总(批准/驳回)","BG_20",null,"BG_30"),BG_ZJLSP("总经理(批准/驳回)","BG_30",null,"BG_40"),BG_ZJBGD("总经办(归档/批准)","BG_40",null,"BG_50");
	
		
	public static final String PASS_Y = "1";
	public static final String PASS_N = "-1";
	public static final String PASS_S = "0";
	
	/**
	 * 节点名称
	 */
    private String name;
	/**
	 * 节点状态
	 */
    private String status;
    /**
     * 审批结果
     */
    private String audit;
    /**
     * 退回到节点
     */
    private String before;
    /**
     * 提交下一个节点
     */
    private String after;
    // 构造方法  
    private EunmTask(String name,String status,String before,String after) {  
        this.name = name;  
        this.status = status; 
        this.before=before;
        this.after=after;
    }
    /**
     * 根据状态码获取任务节点
     * @param status
     * @return
     */
    public static EunmTask getTask(String status) {
    	if(status!=null) {
    		for (EunmTask t : EunmTask.values()) {  
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
    		for (EunmTask t : EunmTask.values()) {  
                if (t.getStatus().equals(status)) {  
                    return t.getName();  
                }  
            }  
    	}
        return null;  
    }

    public static String getBefore(String status) {
    	if(PROJECT_JX.getStatus().equals(status)) {
    		return PROJECT_QD.getStatus();
    	}else if(PROJECT_QD.getStatus().equals(status)) {
    		return PROJECT_PS.getStatus();
    	}else if(PROJECT_PS.getStatus().equals(status)) {
    		return PROJECT_BZ.getStatus();
    	}else if(PROJECT_BZ.getStatus().equals(status)) {
    		return PROJECT_TK.getStatus();
    	}else if(PROJECT_TK.getStatus().equals(status)) {
    		return PROJECT_ZP.getStatus();
    	}else if(PROJECT_ZP.getStatus().equals(status)) {
    		return PROJECT_LX.getStatus();
    	}
    	
    	if(TASK_END.getStatus().equals(status)) {
    		return TASK_GD.getStatus();
    	}else if(TASK_GD.getStatus().equals(status)) {
    		return TASK_FF.getStatus();
    	}else if(TASK_FF.getStatus().equals(status)) {
    		return TASK_PZ.getStatus();
    	}else if(TASK_PZ.getStatus().equals(status)) {
    		return TASK_SH.getStatus();
    	}else if(TASK_SH.getStatus().equals(status)) {
    		return TASK_BZ.getStatus();
    	}else if(TASK_BZ.getStatus().equals(status)) {
    		return TASK_HZ.getStatus();
    	}else if(TASK_HZ.getStatus().equals(status)) {
    		return TASK_XD.getStatus();
    	}else if(TASK_XD.getStatus().equals(status)) {
    		return TASK_JJ.getStatus();
    	}else if(TASK_JJ.getStatus().equals(status)) {
    		return TASK_XC.getStatus();
    	}else if(TASK_XC.getStatus().equals(status)) {
    		return TASK_ZB.getStatus();
    	}else if(TASK_ZB.getStatus().equals(status)) {
    		return TASK_AP.getStatus();
    	}else if(TASK_AP.getStatus().equals(status)) {
    		return TASK_DJ.getStatus();
    	}
    	
    	if(TASK_END.getStatus().equals(status)) {
    		return TASK_B_GD.getStatus();
    	}else if(TASK_B_GD.getStatus().equals(status)) {
    		return TASK_B_QF.getStatus();
    	}else if(TASK_B_QF.getStatus().equals(status)) {
    		return TASK_B_SH.getStatus();
    	}else if(TASK_B_SH.getStatus().equals(status)) {
    		return TASK_B_FH.getStatus();
    	}else if(TASK_B_FH.getStatus().equals(status)) {
    		return TASK_B_BZ.getStatus();
    	}
    	
    	if(ITEM_HZ.getStatus().equals(status)) {
    		return ITEM_JY.getStatus();
    	}else if(ITEM_JY.getStatus().equals(status)) {
    		return ITEM_LR.getStatus();
    	}else if(ITEM_LR.getStatus().equals(status)) {
    		return ITEM_FP.getStatus();
    	} 
    	
    	//行政管理
    	if(BG_ZJBGD.getStatus().equals(status)) {
    		return BG_ZJLSP.getStatus();
    	}else if(BG_ZJLSP.getStatus().equals(status)) {
    		return BG_FZSP.getStatus();
    	}else if(BG_FZSP.getStatus().equals(status)) {
    		return BG_BMSP.getStatus();
    	}else if(BG_BMSP.getStatus().equals(status)) {
    		return BG_SQ.getStatus();
    	}
    	
    	//归档管理
    	if(GD_CY.getStatus().equals(status)) {
    		return GD_QR.getStatus();
    	}else if(GD_QR.getStatus().equals(status)) {
    		return GD_SH.getStatus();
    	}else if(GD_SH.getStatus().equals(status)) {
    		return GD_SQ.getStatus();
    	}
    	
    	
    	return null;
    }
    public static String getAfter(String status) {
    	if(PROJECT_LX.getStatus().equals(status)) {
    		return PROJECT_ZP.getStatus();
    	}else if(PROJECT_ZP.getStatus().equals(status)) {
    		return PROJECT_TK.getStatus();
    	}else if(PROJECT_TK.getStatus().equals(status)) {
    		return PROJECT_BZ.getStatus();
    	}else if(PROJECT_BZ.getStatus().equals(status)) {
    		return PROJECT_PS.getStatus();
    	}else if(PROJECT_PS.getStatus().equals(status)) {
    		return PROJECT_QD.getStatus();
    	}else if(PROJECT_QD.getStatus().equals(status)) {
    		return TASK_END.getStatus();
    	}
    	
    	if(ITEM_FP.getStatus().equals(status)) {
    		return ITEM_LR.getStatus();
    	}else if(ITEM_LR.getStatus().equals(status)) {
    		return ITEM_JY.getStatus();
    	}else if(ITEM_JY.getStatus().equals(status)) {
    		return ITEM_HZ.getStatus();
    	}
    	if(TASK_DJ.getStatus().equals(status)) {
    		return TASK_AP.getStatus();
    	}else if(TASK_AP.getStatus().equals(status)) {
    		return TASK_ZB.getStatus();
    	}else if(TASK_ZB.getStatus().equals(status)) {
    		return TASK_XC.getStatus();
    	}else if(TASK_XC.getStatus().equals(status)) {
    		return TASK_JJ.getStatus();
    	}else if(TASK_JJ.getStatus().equals(status)) {
    		return TASK_XD.getStatus();
    	}else if(TASK_XD.getStatus().equals(status)) {
    		return TASK_HZ.getStatus();
    	}else if(TASK_HZ.getStatus().equals(status)) {
    		return TASK_BZ.getStatus();
    	}else if(TASK_BZ.getStatus().equals(status)) {
    		return TASK_SH.getStatus();
    	}else if(TASK_SH.getStatus().equals(status)) {
    		return TASK_PZ.getStatus();
    	}else if(TASK_PZ.getStatus().equals(status)) {
    		return TASK_FF.getStatus();
    	}else if(TASK_FF.getStatus().equals(status)) {
    		return TASK_GD.getStatus();
    	}else if(TASK_GD.getStatus().equals(status)) {
    		return TASK_END.getStatus();
    	}
    	
    	if(TASK_B_BZ.getStatus().equals(status)) {
    		return TASK_B_FH.getStatus();
    	}else if(TASK_B_FH.getStatus().equals(status)) {
    		return TASK_B_SH.getStatus();
    	}else if(TASK_B_SH.getStatus().equals(status)) {
    		return TASK_B_QF.getStatus();
    	}else if(TASK_B_QF.getStatus().equals(status)) {
    		return TASK_B_GD.getStatus();
    	}else if(TASK_B_GD.getStatus().equals(status)) {
    		return TASK_END.getStatus();
    	}
    	
    	//行政管理
    	if(BG_SQ.getStatus().equals(status)) {
    		return BG_BMSP.getStatus();
    	}else if(BG_BMSP.getStatus().equals(status)) {
    		return BG_FZSP.getStatus();
    	}else if(BG_FZSP.getStatus().equals(status)) {
    		return BG_ZJLSP.getStatus();
    	}else if(BG_ZJLSP.getStatus().equals(status)) {
    		return BG_ZJBGD.getStatus();
    	}
    	
    	//归档管理
    	if(GD_SQ.getStatus().equals(status)) {
    		return GD_SH.getStatus();
    	}else if(GD_SH.getStatus().equals(status)) {
    		return GD_QR.getStatus();
    	}else if(GD_QR.getStatus().equals(status)) {
    		return GD_CY.getStatus();
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
	public String getAudit() {
		return audit;
	}
	public void setAudit(String audit) {
		this.audit = audit;
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
