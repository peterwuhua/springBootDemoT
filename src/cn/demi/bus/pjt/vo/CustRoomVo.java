package cn.demi.bus.pjt.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class CustRoomVo extends Vo<CustRoomVo> {
	
	private CustVo custVo;//客户信息
	private String projectId;//项目
	private String name;//岗位名称
	private int num;//岗位总人数=检测点总人数和
	private String workType;//工作制度
	private String works;//工作内容
	private String workWay;//工作方式
	
	private List<CustMaterialVo> materialList;//物料集合
	private List<CustWorkVo> workList;//写实调查表集合
	private List<CustPointVo> pointList;//采样点/检测对象
	public CustVo getCustVo() {
		return custVo;
	}
	public void setCustVo(CustVo custVo) {
		this.custVo = custVo;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getWorks() {
		return works;
	}
	public void setWorks(String works) {
		this.works = works;
	}
	public String getWorkWay() {
		return workWay;
	}
	public void setWorkWay(String workWay) {
		this.workWay = workWay;
	}
	public List<CustMaterialVo> getMaterialList() {
		return materialList;
	}
	public void setMaterialList(List<CustMaterialVo> materialList) {
		this.materialList = materialList;
	}
	public List<CustWorkVo> getWorkList() {
		return workList;
	}
	public void setWorkList(List<CustWorkVo> workList) {
		this.workList = workList;
	}
	public List<CustPointVo> getPointList() {
		return pointList;
	}
	public void setPointList(List<CustPointVo> pointList) {
		this.pointList = pointList;
	}
}

