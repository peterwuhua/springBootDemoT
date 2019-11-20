package cn.demi.base.system.vo;

import java.util.List;

public class OrgTreeVo {
	private String id;
	private String name;
	private String title;
	private String className;
	private List<OrgTreeVo> children;
	private int leave;
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
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<OrgTreeVo> getChildren() {
		return children;
	}
	public void setChildren(List<OrgTreeVo> children) {
		this.children = children;
	}
	public int getLeave() {
		return leave;
	}
	public void setLeave(int leave) {
		this.leave = leave;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
