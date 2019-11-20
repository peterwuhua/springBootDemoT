package cn.demi.cus.customer.po;

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
 * Description :客户受检单位关联表  <br>
 * @author Danlee Li<br>
 */
@Entity(name = "cus_cus_client")
@Table(name = "cus_cus_client")
@Module(value = "cus.cus_client")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CusClient extends Po<CusClient>{

	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","client","customer"};
	/**
	 * 受检单位
	 */
	private Client client;
	/**
	 * 客户信息
	 */
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(CusClient.class, true, ActionType.JSP);
	}
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
}
