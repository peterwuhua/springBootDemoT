package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Export;
import cn.core.framework.common.vo.Vo;

public class DutyVo extends Vo<DutyVo> {
	/**
	 * 岗位名称
	 */
	@Export(label="岗位名称")
	private String name;// 岗位名称
	/**
	 * 编码
	 */
	@Export(label="编码")
	private String code;// 编码
	/**
	 * 说明
	 */
	@Export(label="说明")
	private String describtion;// 说明

	@Export(label="创建时间",isObject=true)
	private DutyVo dutyVo;
	
	
	public DutyVo getDutyVo() {
		return dutyVo;
	}

	public void setDutyVo(DutyVo dutyVo) {
		this.dutyVo = dutyVo;
	}

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public String getName() {
		return this.name;
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

}