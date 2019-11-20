package cn.demi.cus.customer.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 受检单位点位信息
 * @author QuJunLong
 *
 */
@Entity(name = "cus_client_point")
@Table(name = "cus_client_point")
@Module(value = "cus.clientPoint")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClientPoint extends Po<ClientPoint>{
	
	private static final long serialVersionUID = 5020705621069480292L;
	public String[] PROPERTY_TO_MAP= {"id","sort","client","sampType","room","sampTypeName","sampName","name","code","remark","itemId","itemName","imId"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private Client client;
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
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	@Column(length = 64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length = 32)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length = 64)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(length = 320)
	public String getSampTypeId() {
		return sampTypeId;
	}

	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	@Column(length = 640)
	public String getSampTypeName() {
		return sampTypeName;
	}

	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	@Column(length = 32)
	public String getSampId() {
		return sampId;
	}

	public void setSampId(String sampId) {
		this.sampId = sampId;
	}
	@Column(length = 64)
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 1000)
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Column(length = 1000)
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Column(length = 4000)
	public String getImId() {
		return imId;
	}
	public void setImId(String imId) {
		this.imId = imId;
	}
	@Column(length = 32)
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
	@Column(length = 64)
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ClientPoint.class, true, ActionType.JSP);
	}
}
