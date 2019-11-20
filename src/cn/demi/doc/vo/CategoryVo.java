package cn.demi.doc.vo;

import cn.core.framework.common.vo.Vo;
import cn.core.framework.utils.DateUtils;

/**
 * Create on : 2016年11月24日 下午4:35:02 <br>
 * Description : 文件夹Vo <br>
 * 
 * @version v 1.0.0 <br>
 * @author Dave Yu<br>
 */
public class CategoryVo extends Vo<CategoryVo> {
	/**
	 * 上级文档类型
	 */
	private CategoryVo parentVo;
	/**
	 * 文档类型名称
	 */
	private String name;
	/**
	 * 文档类型编码
	 */
	private String code;
	/**
	 * 创建时间
	 */
	private String time;
	/**
	 * 文档类型说明
	 */
	private String describtion;
	/**
	 * 角色ID
	 */
	private String roleIds;
	/**
	 * 文件夹ID
	 */
	private String dirIds;
	/**
	 * 文件ID
	 */
	private String fileIds;
	/**
	 * 是否为保存按钮
	 */
	public String reSave;
	/**
	 * 操作人
	 */
	private String lastUpdUser;
	/**
	 * 上次操作时间
	 */
	public long lastUpdTime;
	/**
	 * 
	 */
	public String lastUpdTimeStr;

	public CategoryVo getParentVo() {
		return parentVo;
	}

	public void setParentVo(CategoryVo parentVo) {
		this.parentVo = parentVo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public String getRoleIds() {
		return roleIds;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getDirIds() {
		return dirIds;
	}

	public void setDirIds(String dirIds) {
		this.dirIds = dirIds;
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public String getReSave() {
		return reSave;
	}

	public void setReSave(String reSave) {
		this.reSave = reSave;
	}
	
	public String getLastUpdUser() {
		return lastUpdUser;
	}

	public void setLastUpdUser(String lastUpdUser) {
		this.lastUpdUser = lastUpdUser;
	}

	public long getLastUpdTime() {
		return lastUpdTime;
	}

	public void setLastUpdTime(long lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

	public String getLastUpdTimeStr() {
		lastUpdTimeStr = DateUtils.parseToDateStr(lastUpdTime);
		return lastUpdTimeStr;
	}

	public void setLastUpdTimeStr(String lastUpdTimeStr) {
		this.lastUpdTimeStr = lastUpdTimeStr;
	}
	
}
