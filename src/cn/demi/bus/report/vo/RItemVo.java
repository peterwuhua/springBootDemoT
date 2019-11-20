package cn.demi.bus.report.vo;
/**
 * 报告文件 对象
 * 项目类
 * @author QuJunLong
 *
 */
public class RItemVo {

	private String itemName;
	private String testMan;//分析人
	private String testManCard;//证书有效期
	private String methodName;
	private String limitLine;
	private String dws;//点位数
	private String hgs;//合格点位数
	private String yps;//样品数
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getLimitLine() {
		return limitLine;
	}
	public void setLimitLine(String limitLine) {
		this.limitLine = limitLine;
	}
	public String getTestMan() {
		return testMan;
	}
	public void setTestMan(String testMan) {
		this.testMan = testMan;
	}
	public String getTestManCard() {
		return testManCard;
	}
	public void setTestManCard(String testManCard) {
		this.testManCard = testManCard;
	}
	public String getDws() {
		return dws;
	}
	public void setDws(String dws) {
		this.dws = dws;
	}
	public String getHgs() {
		return hgs;
	}
	public void setHgs(String hgs) {
		this.hgs = hgs;
	}
	public String getYps() {
		return yps;
	}
	public void setYps(String yps) {
		this.yps = yps;
	}
}
