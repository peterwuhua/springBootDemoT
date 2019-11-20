package cn.demi.base.system.vo;

import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cn.core.framework.common.vo.Vo;

public class MessageDetailVo extends Vo<MessageDetailVo> {
	/**
	 * 是否已阅读
	 */
	private int readFlag;	//	是否已阅读
	/**
	 * 0 已删除 1 未删除 
	 */
	private int position;	//	0 已删除 1 未删除  
	/**
	 * 阅读时间
	 */
	private String readTime;	//	阅读时间
	/**
	 * 邮件主体
	 */
	private MessageVo messageVo;	// 	邮件主体
	/**
	 * 接收人
	 */
	private String account;		//	接收人
	/**
	 * 类型
	 */
	private String type;	//	类型
	//附件
	private List<FilesVo> fileList;
	
	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@ManyToOne
	@JoinColumn(name = "message_id")
	

	public String getAccount() {
		return account;
	}

	public MessageVo getMessageVo() {
		return messageVo;
	}

	public void setMessageVo(MessageVo messageVo) {
		this.messageVo = messageVo;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<FilesVo> getFileList() {
		return fileList;
	}

	public void setFileList(List<FilesVo> fileList) {
		this.fileList = fileList;
	}
	
	
}
