package cn.demi.bi.task.vo;

/**
 * 人员工作量Vo
 * @author QuJunLong
 *
 */
public class ObjVo {

	private String objId;
	private String objName;//人
	private String deptId;//部门
	private String deptName;
	private int num;//当前工作量
	private int comp;//已完成工作量
	private int ypNum;//已完成样品数
	private int total;//总数
	
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getComp() {
		return comp;
	}
	public void setComp(int comp) {
		this.comp = comp;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getYpNum() {
		return ypNum;
	}
	public void setYpNum(int ypNum) {
		this.ypNum = ypNum;
	}
}
