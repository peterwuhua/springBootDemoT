package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class UploadVo extends Vo<UploadVo> {
	/**
	 * 文件真实名称
	 */
	private String trueName;// 文件真实名称
	/**
	 * 文件存储路径
	 */
	private String path;// 文件存储路径
	/**
	 * 件大小
	 */
	private long size;// 文件大小
	/**
	 * 文件类型
	 */
	private String type;// 文件类型
	/**
	 * 文件属性
	 */
	private String busInfo;// 文件属性
	/**
	 * 业务Id
	 */
	private String busId;// 业务Id
	/**
	 * 上传账户
	 */
	private AccountVo accountVo; // 上传账户

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBusInfo() {
		return busInfo;
	}

	public void setBusInfo(String busInfo) {
		this.busInfo = busInfo;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public AccountVo getAccountVo() {
		return accountVo;
	}

	public void setAccountVo(AccountVo accountVo) {
		this.accountVo = accountVo;
	}

}