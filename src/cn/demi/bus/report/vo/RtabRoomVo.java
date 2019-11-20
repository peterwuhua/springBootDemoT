package cn.demi.bus.report.vo;

import java.util.List;

import cn.demi.bus.test.vo.TestItemVo;
/**
 * 职卫报告文件
 * 车间循环
 * @author QuJunLong
 *
 */
public class RtabRoomVo {

	private String room;
	private String cyType;//采样方式
	private String pc;//采样频次
	private String zyfh;//职业病防护设施及运行情况
	private String grfh;//个人防护用品及佩戴情况
	private List<TestItemVo> itList;
	private List<RtabDataVo> dataList;//点位
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public List<TestItemVo> getItList() {
		return itList;
	}
	public void setItList(List<TestItemVo> itList) {
		this.itList = itList;
	}
	public String getCyType() {
		return cyType;
	}
	public void setCyType(String cyType) {
		this.cyType = cyType;
	}
	public String getPc() {
		return pc;
	}
	public void setPc(String pc) {
		this.pc = pc;
	}
	public String getZyfh() {
		return zyfh;
	}
	public void setZyfh(String zyfh) {
		this.zyfh = zyfh;
	}
	public String getGrfh() {
		return grfh;
	}
	public void setGrfh(String grfh) {
		this.grfh = grfh;
	}
	public List<RtabDataVo> getDataList() {
		return dataList;
	}
	public void setDataList(List<RtabDataVo> dataList) {
		this.dataList = dataList;
	} 
}
