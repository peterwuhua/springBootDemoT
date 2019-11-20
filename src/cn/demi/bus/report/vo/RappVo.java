package cn.demi.bus.report.vo;
/**
 * 报告文件 对象
 * 项目类
 * @author QuJunLong
 *
 */
public class RappVo {

	private String itemName;//项目
	private String appName;//仪器
	private String appRule;//规格
	private String appCode;//编号
	private String appCard;//仪器证书有效期
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppRule() {
		return appRule;
	}
	public void setAppRule(String appRule) {
		this.appRule = appRule;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getAppCard() {
		return appCard;
	}
	public void setAppCard(String appCard) {
		this.appCard = appCard;
	}
}
