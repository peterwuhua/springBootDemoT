package cn.demi.cus.customer.vo;

import cn.core.framework.common.vo.Vo;

public class ClientPointVo extends Vo<ClientPointVo> {
	
	private ClientVo clientVo;
	/**
	 * 样品类别
	 */
	private String sampTypeId;	
	private String sampTypeName;
	private String sampType;//大类
	/**
	 *样品名称
	 */
	private String sampId;
	private String sampName;
	private String room;//车间（职卫）
	/**
	 *点位代码
	 */
	private String code;
	/**
	 *监测点名称
	 */
	private String name;
	/**
	 *类型
	 */
	private String type;
	/**
	 *监测项目
	 */
	private String itemId;
	private String itemName;
	private String imId;//项目方法关系
	private String remark;
	 
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	public String getSampId() {
		return sampId;
	}
	public void setSampId(String sampId) {
		this.sampId = sampId;
	}
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public ClientVo getClientVo() {
		return clientVo;
	}
	public void setClientVo(ClientVo clientVo) {
		this.clientVo = clientVo;
	}
	public String getImId() {
		return imId;
	}
	public void setImId(String imId) {
		this.imId = imId;
	}
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
}

