package cn.demi.qlt.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class NsRecordDetailVo extends Vo<NsRecordDetailVo> {
	private String nsId;//计划
	private String recordId;//记录
	private NsRecordDetailVo parentVo;
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
	
	private List<NsRecordDetailVo> subList;
	public String getNsId() {
		return nsId;
	}
	public void setNsId(String nsId) {
		this.nsId = nsId;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public NsRecordDetailVo getParentVo() {
		return parentVo;
	}
	public void setParentVo(NsRecordDetailVo parentVo) {
		this.parentVo = parentVo;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getRectification() {
		return rectification;
	}
	public void setRectification(String rectification) {
		this.rectification = rectification;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<NsRecordDetailVo> getSubList() {
		return subList;
	}
	public void setSubList(List<NsRecordDetailVo> subList) {
		this.subList = subList;
	}
}

