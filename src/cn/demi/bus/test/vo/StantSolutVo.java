package cn.demi.bus.test.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class StantSolutVo extends Vo<StantSolutVo> {
	private String reagentName;// 试剂名称
	private String reagentId;// 试剂名称
	
	private String plevel;// 级别
	private String piH;// 批号
	private String pzYJ;// 配置依据
	private String tpXh;// 天平型号
	private String code;// 编号
	private String jd;// 精度
	private String wd;// 温度
	private String sd;// 湿度
	private String bctj;// 保存条件
	private String date;// 有效日期
	private String pzJl;// 配置记录
	private String pzr;// 配置人员
	private String pzrId;// 配置人员
	private String pzDate;// 配置日期
	private String jzName;// 基准溶液名称
	private String jzNameId;// 

	private String jzNd;// 基准溶液浓度
	private String jzDate;// 基准溶液配置日期
	private String jzWz;// 基准物质m(g)
	private String bzNd;// 标准滴定溶液浓度
	private String avg1;// 初标均值
	private String avg2;// 复标均值
	private String xdz1;// 极差相对值1（初标）
	private String xdz2;// 极差相对值1（复标）
	private String xdzAll;// 极差相对值2
	private String bz;// 结论
	private String jsgs;// 计算公式
	private String bzAvg;// 标准滴定溶液平均浓度
	private String cbr;// 初标人员
	private String cbrId;// 初标人员
	private String fbr;// 复标人员
	private String fbrId;// 复标人员
	private String bdDate;// 标定日期
	private String ssDate;// 实施日期

	/**
	 * 检测项目
	 */
	private String itemId;
	private String itemName;
	private String taskItemId;
	
	private List<StantSolutMxVo> stantMxList;
	
	
	
	


	public String getPlevel() {
		return plevel;
	}
	public void setPlevel(String plevel) {
		this.plevel = plevel;
	}
	public String getJzNameId() {
		return jzNameId;
	}
	public void setJzNameId(String jzNameId) {
		this.jzNameId = jzNameId;
	}
	public List<StantSolutMxVo> getStantMxList() {
		return stantMxList;
	}
	public void setStantMxList(List<StantSolutMxVo> stantMxList) {
		this.stantMxList = stantMxList;
	}
	public String getTaskItemId() {
		return taskItemId;
	}
	public void setTaskItemId(String taskItemId) {
		this.taskItemId = taskItemId;
	}

	public String getReagentName() {
		return reagentName;
	}
	public void setReagentName(String reagentName) {
		this.reagentName = reagentName;
	}
	public String getReagentId() {
		return reagentId;
	}
	public void setReagentId(String reagentId) {
		this.reagentId = reagentId;
	}

	public String getPiH() {
		return piH;
	}
	public void setPiH(String piH) {
		this.piH = piH;
	}
	public String getPzYJ() {
		return pzYJ;
	}
	public void setPzYJ(String pzYJ) {
		this.pzYJ = pzYJ;
	}
	public String getTpXh() {
		return tpXh;
	}
	public void setTpXh(String tpXh) {
		this.tpXh = tpXh;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getJd() {
		return jd;
	}
	public void setJd(String jd) {
		this.jd = jd;
	}
	public String getWd() {
		return wd;
	}
	public void setWd(String wd) {
		this.wd = wd;
	}
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	public String getBctj() {
		return bctj;
	}
	public void setBctj(String bctj) {
		this.bctj = bctj;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPzJl() {
		return pzJl;
	}
	public void setPzJl(String pzJl) {
		this.pzJl = pzJl;
	}
	public String getPzr() {
		return pzr;
	}
	public void setPzr(String pzr) {
		this.pzr = pzr;
	}
	public String getPzrId() {
		return pzrId;
	}
	public void setPzrId(String pzrId) {
		this.pzrId = pzrId;
	}
	public String getPzDate() {
		return pzDate;
	}
	public void setPzDate(String pzDate) {
		this.pzDate = pzDate;
	}
	public String getJzName() {
		return jzName;
	}
	public void setJzName(String jzName) {
		this.jzName = jzName;
	}
	public String getJzNd() {
		return jzNd;
	}
	public void setJzNd(String jzNd) {
		this.jzNd = jzNd;
	}
	public String getJzDate() {
		return jzDate;
	}
	public void setJzDate(String jzDate) {
		this.jzDate = jzDate;
	}
	public String getJzWz() {
		return jzWz;
	}
	public void setJzWz(String jzWz) {
		this.jzWz = jzWz;
	}
	
	public String getBzNd() {
		return bzNd;
	}
	public void setBzNd(String bzNd) {
		this.bzNd = bzNd;
	}
	public String getAvg1() {
		return avg1;
	}
	public void setAvg1(String avg1) {
		this.avg1 = avg1;
	}
	public String getAvg2() {
		return avg2;
	}
	public void setAvg2(String avg2) {
		this.avg2 = avg2;
	}
	public String getXdz1() {
		return xdz1;
	}
	public void setXdz1(String xdz1) {
		this.xdz1 = xdz1;
	}
	public String getXdz2() {
		return xdz2;
	}
	public void setXdz2(String xdz2) {
		this.xdz2 = xdz2;
	}
	public String getXdzAll() {
		return xdzAll;
	}
	public void setXdzAll(String xdzAll) {
		this.xdzAll = xdzAll;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getJsgs() {
		return jsgs;
	}
	public void setJsgs(String jsgs) {
		this.jsgs = jsgs;
	}
	public String getBzAvg() {
		return bzAvg;
	}
	public void setBzAvg(String bzAvg) {
		this.bzAvg = bzAvg;
	}
	public String getCbr() {
		return cbr;
	}
	public void setCbr(String cbr) {
		this.cbr = cbr;
	}
	public String getCbrId() {
		return cbrId;
	}
	public void setCbrId(String cbrId) {
		this.cbrId = cbrId;
	}
	public String getFbr() {
		return fbr;
	}
	public void setFbr(String fbr) {
		this.fbr = fbr;
	}
	public String getFbrId() {
		return fbrId;
	}
	public void setFbrId(String fbrId) {
		this.fbrId = fbrId;
	}
	public String getBdDate() {
		return bdDate;
	}
	public void setBdDate(String bdDate) {
		this.bdDate = bdDate;
	}
	public String getSsDate() {
		return ssDate;
	}
	public void setSsDate(String ssDate) {
		this.ssDate = ssDate;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	
	
	
}

