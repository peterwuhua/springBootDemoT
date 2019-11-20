package cn.demi.app.init.vo;
/**
 * 监测项目
 * @author user
 *
 */
public class AppInitItem {
	
	private String id;
	private String name;
	private String unit;
	private String code;
	private String isNow;
	private String sampTypeNames;
	private String standNames;
	private String minVal;
	private String 	maxVal;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIsNow() {
		return isNow;
	}

	public void setIsNow(String isNow) {
		this.isNow = isNow;
	}

	public String getSampTypeNames() {
		return sampTypeNames;
	}

	public void setSampTypeNames(String sampTypeNames) {
		this.sampTypeNames = sampTypeNames;
	}

	public String getStandNames() {
		return standNames;
	}

	public void setStandNames(String standNames) {
		this.standNames = standNames;
	}

	public String getMinVal() {
		return minVal;
	}

	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}

	public String getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}

}
