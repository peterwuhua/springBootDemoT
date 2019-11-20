package cn.demi.cus.customer.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 
 * @ClassName:  NormalList   
 * @Description:常用查询字段列表   
 * @author: 吴华 
 * @date:   2019年1月22日 下午2:27:27       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Entity(name = "cus_normalList")
@Table(name = "cus_normalList")
@Module(value = "cus.normalList")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NormalList extends Po<NormalList>{

	private static final long serialVersionUID = 1L;
	
	private String fname; //常用字段名称
	private String cates; //常用字段分类
	private String fid; //常用字段id
	
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"fname","cates","fid"};
	
	public String[] IGNORE_PROPERTY_TO_PO= {"id","createTime","lastUpdTime","version","status"};
	
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo(){
		return IGNORE_PROPERTY_TO_PO;
	}
	@Column(length = 512)
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	@Column(length = 512)
	public String getCates() {
		return cates;
	}
	public void setCates(String cates) {
		this.cates = cates;
	}
	@Column(length = 256)
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	
	
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(NormalList.class, false, ActionType.JSP);
	}


	
	
	
	
	
	
}
