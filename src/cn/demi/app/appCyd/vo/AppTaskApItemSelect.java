package cn.demi.app.appCyd.vo;

/**
 * 选中要添加质控样的项目
 * 
 * @author user
 *
 */
public class AppTaskApItemSelect {

	private String id;
	private String name;
	private String unit;
	private String isNow;
	private String standNames;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getIsNow() {
		return isNow;
	}

	public void setIsNow(String isNow) {
		this.isNow = isNow;
	}

	public String getStandNames() {
		return standNames;
	}

	public void setStandNames(String standNames) {
		this.standNames = standNames;
	}

}
