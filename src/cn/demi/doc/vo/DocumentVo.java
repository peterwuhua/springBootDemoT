package cn.demi.doc.vo;

import cn.core.framework.common.vo.Vo;
import cn.core.framework.utils.DateUtils;

/**
 * Create on : 2016年11月24日 下午4:35:16 <br>
 * Description : 文件Vo <br>
 * 
 * @version v 1.0.0 <br>
 * @author Dave Yu<br>
 */
public class DocumentVo extends Vo<DocumentVo> {
	/**
	 * 文档名称
	 */
	private String name;
	/**
	 * 文档标题
	 */
	private String title;
	/**
	 * 上传时间
	 */
	private String time;
	/**
	 * 文档大小
	 */
	private String size;
	/**
	 * 原始长度
	 */
	private long originalSize;
	/**
	 * 文档标记
	 */
	private String sign;
	/**
	 * 文档说明
	 */
	private String describtion;
	/**
	 * 文档所属类型
	 */
	private CategoryVo categoryVo;
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
	 * 节点路径
	 */
	private String path;
	/**
	 * 文件存放路径(相对路径)
	 */
	private String relativePath;
	/**
	 * 文件类型
	 */
	private String type;
	/**
	 * 取代文件id
	 */
	private String replaceId;
	/**
	 * 取代文件名
	 */
	private String replaceName;
	/**
	 * 文件状态 :现行/作废
	 */
	public String state;
	/**
	 * 有无权限
	 */
	private String isPer;
	/**
	 * 是否所有人可见
	 */
	private String isAllVisible;
	/**
	 * 搜索名
	 */
	private String selectName;
	/**
	 * 是否为保存按钮
	 */
	private String reSave;
	/**
	 * 文件一览表角色
	 */
	private String roleNames;
	/**
	 * 操作人
	 */
	private String lastUpdUser;
	/**
	 * 操作时间
	 */
	private long lastUpdTime;
	/**
	 * 操作时间
	 */
	private String lastUpdTimeStr;
	/**
	 * 是否同步
	 */
	
	private String isSync;
	
	

	public String getIsSync() {
		return isSync;
	}

	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSize() {
		return size;
	}

	public String getDocSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public long getOriginalSize() {
		return originalSize;
	}

	public void setOriginalSize(long originalSize) {
		this.originalSize = originalSize;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public CategoryVo getCategoryVo() {
		return categoryVo;
	}

	public void setCategoryVo(CategoryVo categoryVo) {
		this.categoryVo = categoryVo;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReplaceId() {
		return replaceId;
	}

	public void setReplaceId(String replaceId) {
		this.replaceId = replaceId;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getReplaceName() {
		return replaceName;
	}

	public void setReplaceName(String replaceName) {
		this.replaceName = replaceName;
	}

	public String getIsPer() {
		return isPer;
	}

	public void setIsPer(String isPer) {
		this.isPer = isPer;
	}

	public String getIsAllVisible() {
		return isAllVisible;
	}

	public void setIsAllVisible(String isAllVisible) {
		this.isAllVisible = isAllVisible;
	}

	public String getSelectName() {
		return selectName;
	}

	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}

	public String getReSave() {
		return reSave;
	}

	public void setReSave(String reSave) {
		this.reSave = reSave;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
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
