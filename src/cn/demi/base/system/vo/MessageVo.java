package cn.demi.base.system.vo;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import cn.core.framework.common.vo.Vo;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;

public class MessageVo extends Vo<MessageVo> {
	/**
	 * 发送时间
	 */
	private String sendTime;	//发送时间
	/**
	 * 类型
	 */
	private String type;// 类型
	/**
	 * 发送状态
	 */
	private int position;//发送状态
	/**
	 * 状态
	 */
	private String status;// 状态
	/**
	 * 主题
	 */
	private String subject;//主题
	/**
	 * 内容
	 */
	private String content;//内容
	/**
	 * 发件人
	 */
	private AccountVo senderVo;//发件人
	/**
	 * 发件人
	 */
	private String senderId;//发件人
	/**
	 * 收件人
	 */
	private String reciver;//收件人
	/**
	 * 收件人
	 */
	private String reciverIds;//收件人
    /**
     * 文件
     */
    private MultipartFile[] file;
	//附件
	private List<FilesVo> fileList;
	
	
	private int fj;
	private int sj;
	private int zy;
	private int cg;
	private int lj;

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public AccountVo getSenderVo() {
		return senderVo;
	}

	public void setSenderVo(AccountVo senderVo) {
		this.senderVo = senderVo;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getReciverIds() {
		return reciverIds;
	}

	public void setReciverIds(String reciverIds) {
		this.reciverIds = reciverIds;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	
	public String getSendTimeSDF() {
		String sendTimeSDF="";
		if (!StrUtils.isBlankOrNull(sendTime)) {
			try {
				sendTimeSDF=DateUtils.getRemindDate(sendTime);
			} catch (GlobalException e) {
				sendTimeSDF=sendTime;
				//e.printStackTrace();
				Logger.getLogger(MessageVo.class).info("日期格式错误，转换失败",e);
			}
		}
		return sendTimeSDF;
	}

	public int getFj() {
		return fj;
	}

	public void setFj(int fj) {
		this.fj = fj;
	}

	public int getSj() {
		return sj;
	}

	public void setSj(int sj) {
		this.sj = sj;
	}

	public int getZy() {
		return zy;
	}

	public void setZy(int zy) {
		this.zy = zy;
	}

	public int getCg() {
		return cg;
	}

	public void setCg(int cg) {
		this.cg = cg;
	}

	public int getLj() {
		return lj;
	}

	public void setLj(int lj) {
		this.lj = lj;
	}

	public MultipartFile[] getFile() {
		return file;
	}

	public List<FilesVo> getFileList() {
		return fileList;
	}

	public void setFile(MultipartFile[] file) {
		this.file = file;
	}

	public void setFileList(List<FilesVo> fileList) {
		this.fileList = fileList;
	}
	
}
