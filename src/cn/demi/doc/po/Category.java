package cn.demi.doc.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.TreePo;
import cn.core.framework.utils.code.annotation.Module;

/**
 * Create on : 2016年11月16日 下午4:53:09 <br>
 * Description : 文件夹表  <br>
 * 
 * @version v 1.0.0 <br>
 * @author Dave Yu<br>
 */
@Entity(name = "doc_category")
@Table(name = "doc_category")
@Module(value = "doc.category")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category extends TreePo<Category> {
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","code","time","name","describtion","lastUpdUser","lastUpdTime","createTime"};
	/**
	 * 文档类型编码
	 */
	private String code;
	/**
	 * 创建时间
	 */
	private String time;
	/**
	 * 文档类型说明
	 */
	private String describtion;// 说明
	
	@Column(length=64)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
	

}
