package cn.demi.base.system.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
/**
 * Create on : 2017年2月21日 下午1:21:39  <br>
 * Description :  QuestionsVo<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public class QuestionsVo extends Vo<QuestionsVo> {
	
	private String userId;
	private String userName;//提问人
	private String date;//提问时间
	private String contact;//联系方式
	private String title;//标题
	private String content;//内容
	private String moduleName;//模块
	/**
	 * 搜索问题时传入的搜索参数
	 */
	private String selectName;
	private List<FilesVo> fileList;
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getSelectName() {
		return selectName;
	}
	public void setSelectName(String selectName) {
		this.selectName = selectName;
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
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<FilesVo> getFileList() {
		return fileList;
	}
	public void setFileList(List<FilesVo> fileList) {
		this.fileList = fileList;
	}
}

