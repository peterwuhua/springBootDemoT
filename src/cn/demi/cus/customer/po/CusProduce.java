package cn.demi.cus.customer.po;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;
/**
 * Create on : 2016年11月15日 下午2:49:48  <br>
 * Description : 客户生产单位关联表  <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Entity(name = "cus_cus_produce")
@Table(name = "cus_cus_produce")
@Module(value = "cus.cus_produce")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CusProduce extends Po<CusProduce>{

	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","produce","customer"};
	/**
	 * 生产单位
	 */
	private Produce produce;
	/**
	 * 客户表
	 */
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@ManyToOne
	@JoinColumn(name = "produce_id")
	public Produce getProduce() {
		return produce;
	}
	public void setProduce(Produce produce) {
		this.produce = produce;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
	
}
