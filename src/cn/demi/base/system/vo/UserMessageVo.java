package cn.demi.base.system.vo;

import java.util.Date;

import cn.core.framework.common.vo.Vo;

public class UserMessageVo extends Vo<UserMessageVo> {
	/**
	 * 是否已阅读
	 */
	private int readFlag; // 是否已阅读
	/**
	 * 阅读时间
	 */
	private Date readTime; // 阅读时间
	/**
	 * 0已删除 2收件箱
	 */
	private int position; // 0已删除 2收件箱
	/**
	 * 邮件主体
	 */
	private MessageVo messageVo; // 邮件主体
	/**
	 * 接收账户
	 */
	private AccountVo accountVo; // 接收账户
	/**
	 * 接收账户
	 */
	private String type; // 接收账户

	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public MessageVo getMessageVo() {
		return messageVo;
	}

	public void setMessageVo(MessageVo messageVo) {
		this.messageVo = messageVo;
	}

	public AccountVo getAccountVo() {
		return accountVo;
	}

	public void setAccountVo(AccountVo accountVo) {
		this.accountVo = accountVo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
