package cn.core.framework.constant;

/**
 * 
 * @ClassName: EunmProject
 * @Description: 简易项目流程
 * @author: 吴华
 * @date: 2019年5月7日 上午10:50:34
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
public enum EunmProject {

	PROJECT_END("完成", "finish", null, null), PROJECT_STOP("终止", "stop", null, null),

	PROJECT_LX("项目立项", "XM_00", null, "XM_10"), PROJECT_PS("合同评审", "XM_10", "XM_00", "XM_20"),

	PROJECT_QD("合同签订", "XM_20", "XM_00", "XM_30"), PROJECT_DJ("任务登记", "XM_30", "XM_00", "XM_40"),

	PROJECT_ZP("派发任务", "XM_40", null, "XM_50"), PROJECT_TK("现场踏勘", "XM_50", null, "XM_55"), PROJECT_FA("方案编制", "XM_55", null, "XM_60"), PROJECT_BZ("报告编制",
			"XM_60", "XM_55",
			"XM_70"), PROJECT_BP("报告评审", "XM_70", "XM_60", "XM_80"), PROJECT_BA("提交备案", "XM_80", null, "XM_90"), PROJECT_GD("备案归档", "XM_90", null, "finish");

	public static final String PASS_Y = "1";
	public static final String PASS_N = "-1";
	public static final String PASS_S = "0";

	public static final String ITEM_TYPE_AQ = "安全";
	public static final String ITEM_TYPE_ZW = "职业卫生";
	public static final String ITEM_TYPE_HJ = "环境咨询";

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
	private EunmProject(String name, String status, String before, String after) {
		this.name = name;
		this.status = status;
		this.before = before;
		this.after = after;
	}

	/**
	 * 根据状态码获取任务节点
	 * 
	 * @param status
	 * @return
	 */
	public static EunmProject getTask(String status) {
		if (status != null) {
			for (EunmProject t : EunmProject.values()) {
				if (t.getStatus().equals(status)) {
					return t;
				}
			}
		}
		return null;
	}

	/**
	 * 根据状态码获取任务名称
	 * 
	 * @param status
	 * @return
	 */
	public static String getName(String status) {
		if (status != null) {
			for (EunmProject t : EunmProject.values()) {
				if (t.getStatus().equals(status)) {
					return t.getName();
				}
			}
		}
		return null;
	}

	public static String getBefore(String status) {
		if (PROJECT_END.getStatus().equals(status)) {
			return PROJECT_GD.getStatus();
		} else if (PROJECT_GD.getStatus().equals(status)) {
			return PROJECT_BA.getStatus();
		} else if (PROJECT_BA.getStatus().equals(status)) {
			return PROJECT_BP.getStatus();
		} else if (PROJECT_BP.getStatus().equals(status)) {
			return PROJECT_BZ.getStatus();
		} else if (PROJECT_BZ.getStatus().equals(status)) {
			return PROJECT_FA.getStatus();
		} else if (PROJECT_FA.getStatus().equals(status)) {
			return PROJECT_TK.getStatus();
		} else if (PROJECT_TK.getStatus().equals(status)) {
			return PROJECT_ZP.getStatus();
		} else if (PROJECT_ZP.getStatus().equals(status)) {
			return PROJECT_DJ.getStatus();
		} else if (PROJECT_DJ.getStatus().equals(status)) {
			return PROJECT_QD.getStatus();
		} else if (PROJECT_QD.getStatus().equals(status)) {
			return PROJECT_PS.getStatus();
		} else if (PROJECT_PS.getStatus().equals(status)) {
			return PROJECT_LX.getStatus();
		}

		return null;
	}

	public static String getAfter(String status) {
		if (PROJECT_LX.getStatus().equals(status)) {
			return PROJECT_PS.getStatus();
		} else if (PROJECT_PS.getStatus().equals(status)) {
			return PROJECT_QD.getStatus();
		} else if (PROJECT_QD.getStatus().equals(status)) {
			return PROJECT_DJ.getStatus();
		} else if (PROJECT_DJ.getStatus().equals(status)) {
			return PROJECT_ZP.getStatus();
		} else if (PROJECT_ZP.getStatus().equals(status)) {
			return PROJECT_TK.getStatus();
		} else if (PROJECT_TK.getStatus().equals(status)) {
			return PROJECT_FA.getStatus();
		} else if (PROJECT_FA.getStatus().equals(status)) {
			return PROJECT_BZ.getStatus();
		} else if (PROJECT_BZ.getStatus().equals(status)) {
			return PROJECT_BP.getStatus();
		} else if (PROJECT_BP.getStatus().equals(status)) {
			return PROJECT_BA.getStatus();
		} else if (PROJECT_BA.getStatus().equals(status)) {
			return PROJECT_GD.getStatus();
		} else if (PROJECT_GD.getStatus().equals(status)) {
			return PROJECT_END.getStatus();
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
