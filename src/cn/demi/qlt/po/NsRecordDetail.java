package cn.demi.qlt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 内审要素 记录 详情
 * @author QuJunLong
 *
 */
@Entity(name = "qlt_ns_record_detail")
@Table(name = "qlt_ns_record_detail")
@Module(value = "qlt.nsRecordDetail")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NsRecordDetail extends Po<NsRecordDetail>{

	private static final long serialVersionUID = 8590645147757300039L;
	public String[] PROPERTY_TO_MAP= {"id","sort"};
	private String nsId;//计划
	private String recordId;//记录
	private NsRecordDetail parent;
	private String itemId;//要素Id
	private String code;//要素条款
    private String name;//要素内容
	private int level; //级别
	private int month; //月份
	
	private String result;   //审核结果
	private String question; //存在问题
	private String rectification;    //整改情况
	
	private String userId; //记录人
	private String userName; 
	private String date;  //记录日期
	
	@Column(length=32)
	public String getNsId() {
		return nsId;
	}
	public void setNsId(String nsId) {
		this.nsId = nsId;
	}
	@Column(length=32)
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	@ManyToOne
	@JoinColumn(name = "parent_id")
	public NsRecordDetail getParent() {
		return parent;
	}
	public void setParent(NsRecordDetail parent) {
		this.parent = parent;
	}
	@Column(length=32)
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Column(length=32)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length=256)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=2)
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Column(length=2)
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	@Column(length=320)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Column(length=320)
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	@Column(length=320)
	public String getRectification() {
		return rectification;
	}
	public void setRectification(String rectification) {
		this.rectification = rectification;
	}
	@Column(length=32)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length=32)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=20)
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(NsRecordDetail.class, true, ActionType.JSP);
	}
}
