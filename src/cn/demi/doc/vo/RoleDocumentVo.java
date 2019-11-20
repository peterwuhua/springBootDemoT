package cn.demi.doc.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.RoleVo;

/**
 * Create on : 2016年11月24日 下午4:35:34 <br>
 * Description : 文件授权Vo <br>
 * 
 * @version v 1.0.0 <br>
 * @author Dave Yu<br>
 */
public class RoleDocumentVo extends Vo<RoleDocumentVo> {
	/**
	 * 角色
	 */
	private RoleVo roleVo;
	/**
	 * 文件
	 */
	private DocumentVo documentVo;
	/**
	 * 文件夹
	 */
	private CategoryVo categoryVo;
	/**
	 * 路径
	 */
	private String path;
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
	 * 授权时间
	 */
	private String perTime;
	/**
	 * 文件夹授权Id
	 */
	private String perDirId;
	/**
	 * 是否为保存按钮
	 */
	public String reSave;

	public RoleVo getRoleVo() {
		return roleVo;
	}

	public void setRoleVo(RoleVo roleVo) {
		this.roleVo = roleVo;
	}

	public DocumentVo getDocumentVo() {
		return documentVo;
	}

	public void setDocumentVo(DocumentVo documentVo) {
		this.documentVo = documentVo;
	}

	public String getRoleIds() {
		return roleIds;
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

	public CategoryVo getCategoryVo() {
		return categoryVo;
	}

	public void setCategoryVo(CategoryVo categoryVo) {
		this.categoryVo = categoryVo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPerTime() {
		return perTime;
	}

	public void setPerTime(String perTime) {
		this.perTime = perTime;
	}

	public String getPerDirId() {
		return perDirId;
	}

	public void setPerDirId(String perDirId) {
		this.perDirId = perDirId;
	}

	public String getReSave() {
		return reSave;
	}

	public void setReSave(String reSave) {
		this.reSave = reSave;
	}
}
