package cn.demi.cus.customer.vo;

/**
 * 
 * @ClassName:  Saler   
 * @Description:销售人员   
 * @author: 吴华 
 * @date:   2019年1月24日 下午9:53:28       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
public class Saler {

	private String accountId;//账号id(用它)
	
	private String name;
	
	private String id; //用户id

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	
	
}
