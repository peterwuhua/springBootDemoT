package cn.demi.qlt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 内审要素
 * @author QuJunLong
 *
 */
@Entity(name = "qlt_ns_item")
@Table(name = "qlt_ns_item")
@Module(value = "qlt.nsItem")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NsItem extends Po<NsItem>{

	private static final long serialVersionUID = -7558867518628019017L;
	public String[] PROPERTY_TO_MAP= {"id","sort","code","name","level"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private NsItem item;
	private String code;//条款
    private String name;//内容
	private int level; //级别
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	public NsItem getItem() {
		return item;
	}
	public void setItem(NsItem item) {
		this.item = item;
	}
	@Column(length=20)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length=256)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=2)
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(NsItem.class, true, ActionType.JSP);
	}
}
