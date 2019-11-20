package cn.demi.bus.file.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.TreePo;
import cn.core.framework.utils.code.annotation.Module;

/**
 * Description :档案分类  <br>
 * @version v 1.0.0 <br>
 */
@Entity(name = "bus_archive_type")
@Table(name = "bus_archive_type")
@Module(value = "bus.archiveType")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ArchiveType extends TreePo<ArchiveType> {
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","code","time","name","describtion","lastUpdUser","lastUpdTime","createTime","path"};
	public String[] IGNORE_PROPERTY_TO_PO= {"id","createTime","lastUpdTime","version","level","path"};
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
	@Transient
	@Override
	public String getPath(TreePo<?> po,String path) {
		if(null!=po.getParent()&&po.getParent().getId().equals("0")){
			return po.getName();
		}else if(null==po.getParent()){
			return po.getName();
		}else{
			return getPath(po.getParent(),path)+"/"+po.getName();
		}
	}

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		return IGNORE_PROPERTY_TO_PO;
	}
}
