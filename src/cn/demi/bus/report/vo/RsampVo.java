package cn.demi.bus.report.vo;

import java.util.List;
/**
 * 报告文件 对象
 * 样品类
 * @author QuJunLong
 *
 */
public class RsampVo {

	private String sampName;//样品
	private String hgl;//合格率
	private List<RItemVo> itemList;
	
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public List<RItemVo> getItemList() {
		return itemList;
	}
	public void setItemList(List<RItemVo> itemList) {
		this.itemList = itemList;
	}
	public String getHgl() {
		return hgl;
	}
	public void setHgl(String hgl) {
		this.hgl = hgl;
	}
}
