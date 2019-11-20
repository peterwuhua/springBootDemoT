package cn.demi.cus.customer.vo;

import cn.core.framework.common.vo.Vo;

/**
 * 
 * @ClassName:  NormalListVo   
 * @Description:常用字段vo   
 * @author: 吴华 
 * @date:   2019年1月22日 下午3:13:06       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
public class NormalListVo extends Vo<NormalListVo> {

	private String fname; //常用字段名称
	private String cates; //常用字段分类
	private String fid; //常用字段id
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getCates() {
		return cates;
	}
	public void setCates(String cates) {
		this.cates = cates;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	
	
}

