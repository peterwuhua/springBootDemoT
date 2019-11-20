package cn.core.framework.constant;

public class Constants {

	public static final String SUADMIN= "超级管理员";
	public static final String SUADMIN_LOGIN= "suadmin";
	
	public static final String REPORT_TASKTYPE= "放射性检测";//检测类型
	
	
	
	public static final String S= "是";
	public static final String F= "否";
	
	public static final String Y= "Y";
	public static final String N= "N";
	
	public static final String PASS_Y = "1";
	public static final String PASS_N = "-1";
	public static final String PASS_S = "0";
	
	public static final String ROLE_TESTER = "FXRY";//分析人员
	public static final String ROLE_CYRY = "CYRY";//采样人员
	public static final String ROLE_TK = "XCTKY";//踏勘人员
	public static final String ROLE_CY = "CYFZR";//采样负责人
	//public static final String ROLE_CK = "KCRY";//仓库人员
	//public static final String ROLE_SB = "YQGLY";//设备管理员
	
	public static final String ROLE_REPORT_FPRY = "BGFPY";//报告分配人
	public static final String ROLE_REPORT_MAKER = "BGBZY";//报告编制人
	public static final String ROLE_REPORT_REPT = "BGFHY";//报告复核人
	public static final String ROLE_REPORT_AUDIT = "BGSHY";//报告审核人
	public static final String ROLE_REPORT_SIGN = "BGQFY";//报告签发
	public static final String ROLE_RPT_REPT = "BBFHY";//报表复核人
	public static final String ROLE_RPT_AUDIT = "BBSHY";//报表审核人
	public static final String ROLE_RPT_SIGN = "BBQFY";//报表签发人
	public static final String ROLE_KQ_KQSHR = "KQSHR";//考勤审核人
	//文件上传定义业务类型
	public static final String FILE_TYPE_WORK_REPORT="workrp";//工作简报
	public static final String FILE_TYPE_CM="cm";//行政
	public static final String FILE_TYPE_PROJECT="project";//项目所有资料都存此处，用编号子包区别
	public static final String FILE_TYPE_PROJECT_TK="project_tk";//现场踏勘 附件
	public static final String FILE_TYPE_TASK = "task"; //任务信息
	public static final String FILE_TYPE_TASK_JJ = "task_jj"; //任务交接附件
	public static final String FILE_TYPE_REPORT = "report";//报告流程
	public static final String FILE_TYPE_ITEM = "item";//检测流程
	public static final String FILE_TYPE_ZK = "zk";//质控流程
	public static final String FILE_TYPE_MSG = "message";//邮件附件
	public static final String FILE_TYPE_SUPP = "supplier";//供应商
	public static final String FILE_TYPE_APP = "appara"; //仪器附件 
	public static final String FILE_TYPE_CAR = "car"; //车辆
	public static final String FILE_TYPE_SAMP = "samp"; //任务信息
	public static final String FILE_TYPE_APP_LOGO = "appara_logo"; //仪器附件 
	public static final String FILE_TYPE_APP_QUEST ="questions";//用户体验
	public static final String FILE_TYPE_USER = "user"; //用户附件 
	public static final String FILE_TYPE_ARCHV = "archive"; //档案信息
	public static final String  FILE_TYPE_TASK_POINT = "task_point";//点位图片
	public static final String  FILE_TYPE_TASK_XC_WTDWPTR = "wtdwptr";//受检单位陪同人
	
	//合同状态(合同评审生成合同，合同管理更新合同状态为 签订，任务登记更新合同状态为执行，合同管理更新合同状态为完结)
	public static final String CONTRACT_STATUS_WSC="未生成";//合同状态
	public static final String CONTRACT_STATUS_WQD="未回签";
	public static final String CONTRACT_STATUS_YQD="已回签";
	public static final String CONTRACT_STATUS_ZXZ="执行中";
	public static final String CONTRACT_STATUS_YWJ="已完结";
	public static final String CONTRACT_STATUS_YGQ="已超期";
 
	//余样处理方式
	public static final String SAMP_ZQ="委托方自取";
	public static final String SAMP_YJ="由检测机构代寄，运费到付";
	public static final String SAMP_XH="委托检测机构进行销毁";
	//取报告方式
	public static final String RP_ZQ="自取";
	public static final String RP_YJ="邮寄";
	public static final String RP_SD="送达";
	public static final String RP_QT="其它";
	//样品来源
	public static final String SAMP_XC="外采";
	public static final String SAMP_ZS="送样";
	//采样方式
	public static final String CY_DD="短时间定点";
	public static final String CY_GT="个体采样";
	
	//样品种类
	public static final String SAMP_S="声";
	public static final String SAMP_Q="气";
	public static final String SAMP_W="水";
	public static final String SAMP_T="土";
	public static final String SAMP_G="固";//底泥、沉积物
	
	public static final String SAMP_Q_YZZ="有组织废气";
	public static final String SAMP_Q_WZZ="无组织废气";
	
	public static final String KH_RW_CYAP="RW_CYAP";//采样安排
	public static final String KH_RW_CYZB="RW_CYZB";//采样准备
	public static final String KH_RW_RWXD="RW_RWXD";//任务下达
	public static final String KH_RW_RWFP="RW_RWFP";//任务分配
	public static final String KH_RW_SHLR="RW_SHLR";//数据录入
	public static final String KH_RW_SHJY="RW_SHJY";//数据复核
	public static final String KH_RW_BGBZ="RW_BGBZ";//报告编制
	public static final String KH_RW_BGFH="RW_BGFH";//报告复核
	public static final String KH_RW_BGSH="RW_BGSH";//报告审核
	public static final String KH_RW_BGQF="RW_BGQF";//报告签发
	public static final String KH_RW_BBBZ="RW_BBBZ";//报表编制
	public static final String KH_RW_BBFH="RW_BBFH";//报表复核
	public static final String KH_RW_BBSH="RW_BBSH";//报表审核
	public static final String KH_RW_BBQF="RW_BBQF";//报表签发
	
	//发票状态
	public static final String BILL_STATUS_WSP = "提交中"; 
	public static final String BILL_STATUS_WTG = "审批不通过";
	public static final String BILL_STATUS_WKJ = "未开据";
	public static final String BILL_STATUS_YKJ = "已开据";
	
	//收费状态（收费管理更新合同状态）
	public static final String CHARGE_STATUS_WSF="未收费";
	public static final String CHARGE_STATUS_YYF="已预付";
	public static final String CHARGE_STATUS_SFZ="未完结";
	public static final String CHARGE_STATUS_YWJ="已完结";
	
	//客户状态
	public static final String CUS_CUST_STATUS_WTG="审批不通过"; 
	public static final String CUS_CUST_STATUS_QSZT="起始状态"; 
	public static final String CUS_CUST_STATUS_XZ="自己新增的"; 
	public static final String CUS_CUST_STATUS_SPTG="经理审批通过"; 
	public static final String CUS_CUST_STATUS_ZP="经理指派"; 
	

	
	//客户跟进状态
	public static final String CUS_GJ_STATUS_WQ="未签合同";
	public static final String CUS_GJ_STATUS_YQ="已签合同";
	
	//客户类型
	public static final String CUS_CATES_JQ="近期服务客户";
	public static final String CUS_CATES_ZQ="中期服务客户";
	public static final String CUS_CATES_CQ="长期服务客户";
	public static final String CUS_CATES_DKH="大客户";
	
	//客户审核
	public static final String CUS_AUDIT_SPZ="审批中"; 
	public static final String CUS_AUDIT_YTG="审批通过"; 
	
	
	//客户回访
	public static final String CUS_VISIT_STATUS_DHF ="待回访";
	public static final String CUS_VISIT_STATUS_YHF ="已回访";
	
	
//	//会务状态
//	public static final String OFFICE_HW_STATUS_WSQ = "未申请";
//	public static final String OFFICE_HW_STATUS_WSP = "未审批";
//	public static final String OFFICE_HW_STATUS_SP_BTG = "审批不通过";
//	public static final String OFFICE_HW_STATUS_SP_WCY = "未查阅";
//	public static final String OFFICE_HW_STATUS_SP_YCY = "已查阅";
	
	//请假状态
	public static final String QJGL_STATUS_WSQ = "未申请";
	public static final String QJGL_STATUS_WSP = "未审批";
	public static final String QJGL_STATUS_SP_BTG = "审批不通过";
	public static final String QJGL_STATUS_SP_TG = "审批通过";
	public static final String QJGL_STATUS_YXJ = "已销假";
	
	
	
}
